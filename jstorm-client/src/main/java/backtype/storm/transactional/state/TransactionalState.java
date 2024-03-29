package backtype.storm.transactional.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import backtype.storm.Config;
import backtype.storm.serialization.KryoValuesDeserializer;
import backtype.storm.serialization.KryoValuesSerializer;
import backtype.storm.utils.Utils;

public class TransactionalState {
	CuratorFramework _curator;
	KryoValuesSerializer _ser;
	KryoValuesDeserializer _des;

	public static TransactionalState newUserState(Map conf, String id,
			Map componentConf) {
		return new TransactionalState(conf, id, componentConf, "user");
	}

	public static TransactionalState newCoordinatorState(Map conf, String id,
			Map componentConf) {
		return new TransactionalState(conf, id, componentConf, "coordinator");
	}

	protected TransactionalState(Map conf, String id, Map componentConf,
			String subroot) {
		try {
			conf = new HashMap(conf);
			// ensure that the serialization registrations are consistent with
			// the declarations in this spout
			if (componentConf != null) {
				conf.put(Config.TOPOLOGY_KRYO_REGISTER,
						componentConf.get(Config.TOPOLOGY_KRYO_REGISTER));
			}
			String rootDir = conf.get(Config.TRANSACTIONAL_ZOOKEEPER_ROOT)
					+ "/" + id + "/" + subroot;
			List<String> servers = (List<String>) getWithBackup(conf,
					Config.TRANSACTIONAL_ZOOKEEPER_SERVERS,
					Config.STORM_ZOOKEEPER_SERVERS);
			Object port = getWithBackup(conf,
					Config.TRANSACTIONAL_ZOOKEEPER_PORT,
					Config.STORM_ZOOKEEPER_PORT);
			CuratorFramework initter = Utils.newCuratorStarted(conf, servers,
					port);
			try {
				initter.create().creatingParentsIfNeeded().forPath(rootDir);
			} catch (KeeperException.NodeExistsException e) {

			}

			initter.close();

			_curator = Utils.newCuratorStarted(conf, servers, port, rootDir);
			_ser = new KryoValuesSerializer(conf);
			_des = new KryoValuesDeserializer(conf);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setData(String path, Object obj) {
		path = "/" + path;
		byte[] ser = _ser.serializeObject(obj);
		try {
			if (_curator.checkExists().forPath(path) != null) {
				_curator.setData().forPath(path, ser);
			} else {
				_curator.create().creatingParentsIfNeeded()
						.withMode(CreateMode.PERSISTENT).forPath(path, ser);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(String path) {
		path = "/" + path;
		try {
			_curator.delete().forPath(path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> list(String path) {
		path = "/" + path;
		try {
			if (_curator.checkExists().forPath(path) == null) {
				return new ArrayList<String>();
			} else {
				return _curator.getChildren().forPath(path);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void mkdir(String path) {
		setData(path, 7);
	}

	public Object getData(String path) {
		path = "/" + path;
		try {
			if (_curator.checkExists().forPath(path) != null) {
				return _des.deserializeObject(_curator.getData().forPath(path));
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void close() {
		_curator.close();
	}

	private Object getWithBackup(Map amap, Object primary, Object backup) {
		Object ret = amap.get(primary);
		if (ret == null)
			return amap.get(backup);
		return ret;
	}
}
