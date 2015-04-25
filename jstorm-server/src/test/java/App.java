import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.alibaba.jstorm.cluster.Cluster;
import com.alibaba.jstorm.task.Assignment;

public class App {

    // 会话超时时间，设置为与系统默认时间一致

    private static final int SESSION_TIMEOUT = 30000;

    // 创建 ZooKeeper 实例

    ZooKeeper                zk;

    // 创建 Watcher 实例

    Watcher                  wh              = new Watcher() {

                                                 public void process(org.apache.zookeeper.WatchedEvent event) {
                                                     System.out.println("event=" + event.toString());
                                                 }

                                             };

    // 初始化 ZooKeeper 实例

    private void createZKInstance() throws Exception {
        zk = new ZooKeeper("10.139.163.31:2181", App.SESSION_TIMEOUT, this.wh);
    }

    private void ZKOperations() throws IOException, InterruptedException, KeeperException {

        byte[] data = zk.getData("/jstorm/assignments/WordCountTopology-1-1429782147", false, null);
        Object dataNode = Cluster.maybe_deserialize(data);
        // ((Assignment) dataNode).getMasterCodeDir()
        Assignment assignment = (Assignment) dataNode;

    }

    private void ZKClose() throws InterruptedException {
        zk.close();
    }

    public static void main(String[] args) throws Exception {
        App dm = new App();
        dm.createZKInstance();
        dm.ZKOperations();
        dm.ZKClose();
    }

}
