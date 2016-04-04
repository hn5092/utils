package xym.hbase.api;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

public class Admin {
	private static final Log LOG = LogFactory.getLog(Admin.class);
	private static final byte[] INFO = Bytes.toBytes("info");
	private static final byte[] URL = Bytes.toBytes("URL");
	private static final byte[] HINT1 = Bytes.toBytes("hint1");
	private static final byte[] HINT2 = Bytes.toBytes("hint2");

	private static final byte[] ROW1 = Bytes.toBytes("111");
	private static final byte[] SCREEN = Bytes.toBytes("screen");
	private static final byte[] ROW2 = Bytes.toBytes("2222");

	Configuration conf;
	HTable table;
	Scan scan;
	HBaseAdmin admin;
	@Before
	public void setup() {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "xym01:2181,xym02:2181,xym03:2181");
		try {
			 admin = new HBaseAdmin(conf);
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testStatus(){
		try {
			ClusterStatus clusterStatus = admin.getClusterStatus();
			admin.compact("test1");
			System.out.println(clusterStatus.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
