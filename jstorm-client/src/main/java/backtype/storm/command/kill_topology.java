package backtype.storm.command;

import java.security.InvalidParameterException;
import java.util.Map;

import backtype.storm.generated.KillOptions;
import backtype.storm.utils.NimbusClient;
import backtype.storm.utils.Utils;

/**
 * Kill topology
 * 
 * @author longda
 * 
 */
public class kill_topology {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args == null || args.length == 0) {
			throw new InvalidParameterException("Should input topology name");
		}

		String topologyName = args[0];

		NimbusClient client = null;
		try {

			Map conf = Utils.readStormConfig();
			client = NimbusClient.getConfiguredClient(conf);

			if (args.length == 1) {

				client.getClient().killTopology(topologyName);
			} else {
				int delaySeconds = Integer.parseInt(args[1]);

				KillOptions options = new KillOptions();
				options.set_wait_secs(delaySeconds);

				client.getClient().killTopologyWithOpts(topologyName, options);

			}

			System.out.println("Successfully submit command kill "
					+ topologyName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

}
