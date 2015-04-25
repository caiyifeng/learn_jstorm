package com.alibaba.jstorm.daemon.nimbus;

import java.io.IOException;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.thrift7.protocol.TBinaryProtocol;
import org.apache.thrift7.server.THsHaServer;
import org.apache.thrift7.transport.TNonblockingServerSocket;
import org.apache.thrift7.transport.TTransportException;

import backtype.storm.Config;
import backtype.storm.generated.Nimbus;
import backtype.storm.generated.Nimbus.Iface;
import backtype.storm.scheduler.INimbus;
import backtype.storm.utils.BufferFileInputStream;
import backtype.storm.utils.TimeCacheMap;
import backtype.storm.utils.Utils;

import com.alibaba.jstorm.callback.AsyncLoopThread;
import com.alibaba.jstorm.client.ConfigExtension;
import com.alibaba.jstorm.cluster.StormConfig;
import com.alibaba.jstorm.daemon.supervisor.Httpserver;
import com.alibaba.jstorm.daemon.worker.hearbeat.SyncContainerHb;
import com.alibaba.jstorm.daemon.worker.metrics.AlimonitorClient;
import com.alibaba.jstorm.daemon.worker.metrics.MetricSendClient;
import com.alibaba.jstorm.daemon.worker.metrics.UploadMetricFromZK;
import com.alibaba.jstorm.schedule.CleanRunnable;
import com.alibaba.jstorm.schedule.FollowerRunnable;
import com.alibaba.jstorm.schedule.MonitorRunnable;
import com.alibaba.jstorm.utils.JStormServerUtils;
import com.alibaba.jstorm.utils.JStormUtils;

/**
 * NimbusServer work flow: 1. cleanup interrupted topology delete
 * /storm-local-dir/nimbus/topologyid/stormdis delete
 * /storm-zk-root/storms/topologyid 2. set /storm-zk-root/storms/topology stats
 * as run 3. start one thread, every nimbus.monitor.reeq.secs set
 * /storm-zk-root/storms/ all topology as monitor. when the topology's status is
 * monitor, nimubs would reassign workers 4. start one threa, every
 * nimubs.cleanup.inbox.freq.secs cleanup useless jar
 * 
 * @author version 1: Nathan Marz version 2: Lixin/Chenjun version 3: Longda
 */
public class NimbusServer {

    private static final Logger   LOG          = Logger.getLogger(NimbusServer.class);

    private NimbusData            data;

    private ServiceHandler        serviceHandler;

    private TopologyAssign        topologyAssign;

    private THsHaServer           thriftServer;

    private FollowerRunnable      follower;

    private Httpserver            hs;

    private UploadMetricFromZK    uploadMetric;

    private List<AsyncLoopThread> smartThreads = new ArrayList<AsyncLoopThread>();

    public static void main(String[] args) throws Exception {
        // read configuration files
        @SuppressWarnings("rawtypes")
        /**
         * 读取配置文件和启动参数
         * 首先会读取默认配置jstorm-server-X.X.X.X.jar中的defaults.yaml
         * 如果没有设置storm.conf.file参数
         * 会默认读取storm.yaml
         */
        Map config = Utils.readStormConfig();

        /**
         * 此处貌似是后续功能？
         */
        JStormServerUtils.startTaobaoJvmMonitor();

        NimbusServer instance = new NimbusServer();

        /**
         * TODO:后续查看一下Inimbus代码
         */
        INimbus iNimbus = new DefaultInimbus();

        /**
         * 驱动服务
         */
        instance.launchServer(config, iNimbus);

    }

    /**
     * 1.在建立${storm.local.dir}/nimbus/pids目录
     * ${storm.local.dir}在storm.yaml内配置
     * 2.通过解析ManagementFactory.getRuntimeMXBean().getName()获得jvm的pid
     * 生成pid文件,kill pids目录下面其他的pid进程，并删除pid文件
     */
    private void createPid(Map conf) throws Exception {
        String pidDir = StormConfig.masterPids(conf);

        JStormServerUtils.createPid(pidDir);
    }

    @SuppressWarnings("rawtypes")
    private void launchServer(final Map conf, INimbus inimbus) {
        LOG.info("Begin to start nimbus with conf " + conf);

        try {
            // 1. check whether mode is distributed or not
            /**
             * 判断启动模式是分布式还是本地
             * 判断依据为配置项storm.cluster.mode
             * local : 本地
             * distributed : 分布式
             */
            StormConfig.validate_distributed_mode(conf);

            createPid(conf);

            initShutdownHook();

            /**
             * 尚未开发？
             */
            inimbus.prepare(conf, StormConfig.masterInimbus(conf));

            data = createNimbusData(conf, inimbus);

            initFollowerThread(conf);

            /**
             * 在7621端口启动httpServer
             */
            int port = ConfigExtension.getNimbusDeamonHttpserverPort(conf);
            hs = new Httpserver(port, conf);
            hs.start();

            initContainerHBThread(conf);

            while (!data.isLeader())
                Utils.sleep(5000);

            initUploadMetricThread(data);

            init(conf);
        } catch (Throwable e) {
            LOG.error("Fail to run nimbus ", e);
        } finally {
            cleanup();
        }

        LOG.info("Quit nimbus");
    }

    public ServiceHandler launcherLocalServer(final Map conf, INimbus inimbus) throws Exception {
        LOG.info("Begin to start nimbus on local model");

        StormConfig.validate_local_mode(conf);

        inimbus.prepare(conf, StormConfig.masterInimbus(conf));

        data = createNimbusData(conf, inimbus);

        init(conf);

        return serviceHandler;
    }

    /**
     * 判断是否运行在Apsara/Yarn上
     * 根据container.nimbus.heartbeat配置
     * 如果是则返回运行的线程
     */
    private void initContainerHBThread(Map conf) throws IOException {
        AsyncLoopThread thread = SyncContainerHb.mkNimbusInstance(conf);
        if (thread != null) {
            smartThreads.add(thread);
        }
    }

    private void init(Map conf) throws Exception {

        NimbusUtils.cleanupCorruptTopologies(data);

        initTopologyAssign();

        initTopologyStatus();

        initCleaner(conf);

        serviceHandler = new ServiceHandler(data);

        if (!data.isLocalMode()) {

            initMonitor(conf);

            initThrift(conf);

        }
    }

    /**
     * 1.此处定义一个TimcacheMap缓存失效时候的callback函数，关闭channel和stream
     * 建立上传和下载两个缓存，缓存流
     * 新建一个Nimbus对象
     */
    @SuppressWarnings("rawtypes")
    private NimbusData createNimbusData(Map conf, INimbus inimbus) throws Exception {

        TimeCacheMap.ExpiredCallback<Object, Object> expiredCallback = new TimeCacheMap.ExpiredCallback<Object, Object>() {
            @Override
            public void expire(Object key, Object val) {
                try {
                    LOG.info("Close file " + String.valueOf(key));
                    if (val != null) {
                        if (val instanceof Channel) {
                            Channel channel = (Channel) val;
                            channel.close();
                        } else if (val instanceof BufferFileInputStream) {
                            BufferFileInputStream is = (BufferFileInputStream) val;
                            is.close();
                        }
                    }
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }

            }
        };

        int file_copy_expiration_secs = JStormUtils.parseInt(conf.get(Config.NIMBUS_FILE_COPY_EXPIRATION_SECS), 30);
        TimeCacheMap<Object, Object> uploaders = new TimeCacheMap<Object, Object>(file_copy_expiration_secs,
                expiredCallback);
        TimeCacheMap<Object, Object> downloaders = new TimeCacheMap<Object, Object>(file_copy_expiration_secs,
                expiredCallback);

        // Callback callback=new TimerCallBack();
        // StormTimer timer=Timer.mkTimerTimer(callback);
        NimbusData data = new NimbusData(conf, downloaders, uploaders, inimbus);

        return data;

    }

    private void initTopologyAssign() {
        topologyAssign = TopologyAssign.getInstance();
        topologyAssign.init(data);
    }

    private void initTopologyStatus() throws Exception {
        // get active topology in ZK
        List<String> active_ids = data.getStormClusterState().active_storms();

        if (active_ids != null) {

            for (String topologyid : active_ids) {
                // set the topology status as startup
                // in fact, startup won't change anything
                NimbusUtils.transition(data, topologyid, false, StatusType.startup);
            }

        }

        LOG.info("Successfully init topology status");
    }

    @SuppressWarnings("rawtypes")
    private void initMonitor(Map conf) {
        final ScheduledExecutorService scheduExec = data.getScheduExec();

        // Schedule Nimbus monitor
        MonitorRunnable r1 = new MonitorRunnable(data);

        int monitor_freq_secs = JStormUtils.parseInt(conf.get(Config.NIMBUS_MONITOR_FREQ_SECS), 10);
        scheduExec.scheduleAtFixedRate(r1, 0, monitor_freq_secs, TimeUnit.SECONDS);

        LOG.info("Successfully init Monitor thread");
    }

    /**
     * Right now, every 600 seconds, nimbus will clean jar under
     * /LOCAL-DIR/nimbus/inbox, which is the uploading topology directory
     * 
     * @param conf
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private void initCleaner(Map conf) throws IOException {
        final ScheduledExecutorService scheduExec = data.getScheduExec();

        // Schedule Nimbus inbox cleaner/nimbus/inbox jar
        String dir_location = StormConfig.masterInbox(conf);
        int inbox_jar_expiration_secs = JStormUtils.parseInt(conf.get(Config.NIMBUS_INBOX_JAR_EXPIRATION_SECS), 3600);
        CleanRunnable r2 = new CleanRunnable(dir_location, inbox_jar_expiration_secs);

        int cleanup_inbox_freq_secs = JStormUtils.parseInt(conf.get(Config.NIMBUS_CLEANUP_INBOX_FREQ_SECS), 600);

        scheduExec.scheduleAtFixedRate(r2, 0, cleanup_inbox_freq_secs, TimeUnit.SECONDS);

        LOG.info("Successfully init " + dir_location + " cleaner");
    }

    @SuppressWarnings("rawtypes")
    private void initThrift(Map conf) throws TTransportException {
        Integer thrift_port = JStormUtils.parseInt(conf.get(Config.NIMBUS_THRIFT_PORT));
        TNonblockingServerSocket socket = new TNonblockingServerSocket(thrift_port);

        Integer maxReadBufSize = JStormUtils.parseInt(conf.get(Config.NIMBUS_THRIFT_MAX_BUFFER_SIZE));

        THsHaServer.Args args = new THsHaServer.Args(socket);
        args.workerThreads(ServiceHandler.THREAD_NUM);
        args.protocolFactory(new TBinaryProtocol.Factory(false, true, maxReadBufSize));

        args.processor(new Nimbus.Processor<Iface>(serviceHandler));
        args.maxReadBufferBytes = maxReadBufSize;

        thriftServer = new THsHaServer(args);

        LOG.info("Successfully started nimbus: started Thrift server...");
        thriftServer.serve();
    }

    private void initFollowerThread(Map conf) {
        follower = new FollowerRunnable(data, 5000);
        Thread thread = new Thread(follower);
        thread.setDaemon(true);
        thread.start();
        LOG.info("Successfully init Follower thread");
    }

    /**
     * 新建一个线程来做监视关闭nimbus
     */
    private void initShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                NimbusServer.this.cleanup();
            }

        });
    }

    private void initUploadMetricThread(NimbusData data) {
        ScheduledExecutorService scheduleService = data.getScheduExec();

        MetricSendClient client;
        if (ConfigExtension.isAlimonitorMetricsPost(data.getConf())) {
            client = new AlimonitorClient(AlimonitorClient.DEFAUT_ADDR, AlimonitorClient.DEFAULT_PORT, true);
        } else {
            client = new MetricSendClient();
        }

        uploadMetric = new UploadMetricFromZK(data, client);

        scheduleService.scheduleWithFixedDelay(uploadMetric, 120, 60, TimeUnit.SECONDS);

        LOG.info("Successfully init metrics uploading thread");
    }

    public void cleanup() {
        if (data.getIsShutdown().getAndSet(true) == true) {
            LOG.info("Notify to quit nimbus");
            return;
        }

        LOG.info("Begin to shutdown nimbus");

        for (AsyncLoopThread t : smartThreads) {

            t.cleanup();
            JStormUtils.sleepMs(10);
            t.interrupt();
            // try {
            // t.join();
            // } catch (InterruptedException e) {
            // LOG.error("join thread", e);
            // }
            LOG.info("Successfully cleanup " + t.getThread().getName());
        }

        if (serviceHandler != null) {
            serviceHandler.shutdown();
        }

        if (topologyAssign != null) {
            topologyAssign.cleanup();
            LOG.info("Successfully shutdown TopologyAssign thread");
        }

        if (follower != null) {
            follower.clean();
            LOG.info("Successfully shutdown follower thread");
        }

        if (uploadMetric != null) {
            uploadMetric.clean();
            LOG.info("Successfully shutdown UploadMetric thread");
        }

        if (data != null) {
            data.cleanup();
            LOG.info("Successfully shutdown NimbusData");
        }

        if (thriftServer != null) {
            thriftServer.stop();
            LOG.info("Successfully shutdown thrift server");
        }

        if (hs != null) {
            hs.shutdown();
            LOG.info("Successfully shutdown httpserver");
        }

        LOG.info("Successfully shutdown nimbus");
        // make sure shutdown nimbus
        JStormUtils.halt_process(0, "!!!Shutdown!!!");

    }

}
