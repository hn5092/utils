package hadoop.yarn.yarnapp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.NodeReport;
import org.apache.hadoop.yarn.api.records.NodeState;
import org.apache.hadoop.yarn.api.records.YarnClusterMetrics;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.client.api.async.AMRMClientAsync.CallbackHandler;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyApplicationMaster {
	private static final Log LOG = LogFactory.getLog(MyApplicationMaster.class);
	static YarnClient yarnClient;
	static YarnConfiguration yarnConf;

	@BeforeClass
	public static void setup() {
		yarnConf = new YarnConfiguration(new Configuration());
		CallbackHandler callbackHandler = new MyCallbackHandle();
		InetSocketAddress socketAddr = yarnConf.getSocketAddr(
				YarnConfiguration.RM_ADDRESS,
				YarnConfiguration.DEFAULT_RM_ADDRESS,
				YarnConfiguration.DEFAULT_RM_PORT);
		System.out.println(socketAddr.getHostName() + "|"
				+ socketAddr.getHostString() + "|" + socketAddr.getPort() + "|"
				+ socketAddr.getAddress());
		yarnConf.setSocketAddr("yarn.resourcemanager.address",
				new InetSocketAddress("192.168.80.103", 8032));
		socketAddr = yarnConf.getSocketAddr(YarnConfiguration.RM_ADDRESS,
				YarnConfiguration.DEFAULT_RM_ADDRESS,
				YarnConfiguration.DEFAULT_RM_PORT);
		System.out.println(socketAddr.getHostName() + "|"
				+ socketAddr.getHostString() + "|" + socketAddr.getPort() + "|"
				+ socketAddr.getAddress());
	}

	@Test
	public void testClient() {
		YarnClient yarnClient = YarnClient.createYarnClient();
		yarnClient.init(yarnConf);
		yarnClient.start();
		YarnClusterMetrics clusterMetrics;
		try {
			clusterMetrics = yarnClient.getYarnClusterMetrics();
			System.out.println("this is the cluster name : "
					+ yarnClient.getName());
			LOG.info("Got Cluster metric info from ASM" + ", numNodeManagers="
					+ clusterMetrics.getNumNodeManagers());
			//get  
			List<NodeReport> clusterNodeReports = yarnClient
					.getNodeReports(NodeState.RUNNING);
			LOG.info("Got Cluster node info from ASM");
			for (NodeReport node : clusterNodeReports) {
				LOG.info("Got node report from ASM for" + ", nodeId="
						+ node.getNodeId() + ", nodeAddress"
						+ node.getHttpAddress() + ", nodeRackName"
						+ node.getRackName() + ", nodeNumContainers"
						+ node.getNumContainers() + "nodegetCapability" + node.getCapability());
			}
			
		} catch (YarnException | IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAppMaster() {
		
	}
}
