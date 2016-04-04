package xym.hbase.api;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Pair;
import org.junit.Before;
import org.junit.Test;

public class HbaseDDL {
	private static final Log LOG = LogFactory.getLog(HbaseDDL.class);
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

	@Before
	public void setup() {
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "xym01:2181,xym02:2181,xym03:2181");
	}

	/**
	 * 创建一个表
	 */
	@Test
	public void testCreateTable() {
		try {
			HBaseAdmin ha = new HBaseAdmin(conf);
			HTableDescriptor htd = new HTableDescriptor(
					TableName.valueOf("test"));
			HColumnDescriptor hcDescriptor = new HColumnDescriptor("info");
			HColumnDescriptor hcDescriptor2 = new HColumnDescriptor("data");
			hcDescriptor.setMaxVersions(3);
			htd.addFamily(hcDescriptor);
			htd.addFamily(hcDescriptor2);
			ha.createTable(htd);
			ha.close();
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 创建一个表并且分区 从1 到100 每个区委10 分区的最小个数是3个 起始位置和末尾都是空值
	 */
	@Test
	public void testRegion() {
		try {
			HBaseAdmin ha = new HBaseAdmin(conf);
			String tableName = "test9";
			HTableDescriptor htd = new HTableDescriptor(
					TableName.valueOf(tableName));
			HColumnDescriptor hcDescriptor = new HColumnDescriptor("info");
			HColumnDescriptor hcDescriptor2 = new HColumnDescriptor("data");
			hcDescriptor.setMaxVersions(3);
			htd.addFamily(hcDescriptor);
			htd.addFamily(hcDescriptor2);
			ha.createTable(htd, Bytes.toBytes(1L), Bytes.toBytes(100000l),5);
			ha.close();
			printRegions(tableName);
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 手动设置分区 分区的个数是length+1
	 * 
	 */
	@Test
	public void testRegion2() {
		try {
			HBaseAdmin ha = new HBaseAdmin(conf);
			String tableName = "test2";
			HTableDescriptor htd = new HTableDescriptor(
					TableName.valueOf(tableName));
			HColumnDescriptor hcDescriptor = new HColumnDescriptor("info");
			HColumnDescriptor hcDescriptor2 = new HColumnDescriptor("data");
			hcDescriptor.setMaxVersions(3);
			htd.addFamily(hcDescriptor);
			htd.addFamily(hcDescriptor2);
			byte[][] regions = new byte[][] { Bytes.toBytes("A"),
					Bytes.toBytes("B"), Bytes.toBytes("C"), Bytes.toBytes("D"),
					Bytes.toBytes("E") };
			ha.createTable(htd, regions);
			ha.close();
			printRegions(tableName);
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 */

	@Test
	public void testAsyncTable3() {
		try {
			HBaseAdmin ha = new HBaseAdmin(conf);
			String tableName = "test1";
			HTableDescriptor htd = new HTableDescriptor(
					TableName.valueOf(tableName));
			HColumnDescriptor hcDescriptor = new HColumnDescriptor("info");
			HColumnDescriptor hcDescriptor2 = new HColumnDescriptor("data");
			hcDescriptor.setMaxVersions(3);
			htd.addFamily(hcDescriptor);
			htd.addFamily(hcDescriptor2);
			byte[][] regions = new byte[][] { Bytes.toBytes("A"),
					Bytes.toBytes("B"), Bytes.toBytes("C"), Bytes.toBytes("D"),
					Bytes.toBytes("E") };
			// 异步创建表
			ha.createTableAsync(htd, regions);
			System.out
					.println("-------------table is exists------------------");
			System.out.println(ha.tableExists(tableName));
			System.out
					.println("--------------table descriptor-----------------");
			System.out.println(ha.getTableDescriptor(Bytes.toBytes(tableName)));
			ha.close();
			printRegions(tableName);
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 打印分区信息1
	 * 
	 * @param tableName
	 */
	public void printRegions(String tableName) {
		try {
			HTable table = new HTable(conf, Bytes.toBytes(tableName));
			Pair<byte[][], byte[][]> startEndKeys = table.getStartEndKeys();
			System.out
					.println("-------------table region -----------------------");
			for (int i = 0; i < startEndKeys.getFirst().length; i++) {
				byte[] bs = startEndKeys.getFirst()[i];
				byte[] bs2 = startEndKeys.getSecond()[i];
				System.out.println("["
						+ (i + 1)
						+ "]"
						+ "start key:"
						+ (bs.length == 8 ? Bytes.toLong(bs) : Bytes
								.toString(bs))
						+ ",end key:"
						+ (bs2.length == 8 ? Bytes.toLong(bs2) : Bytes
								.toString(bs2)));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 删除表 删除之前要下把表disable 之后才可以删除表
	 */
	@Test
	public void deleteTable() {
		String tableName = "test";
		try {
			HBaseAdmin ha = new HBaseAdmin(conf);
			ha.disableTable(tableName);
			// ha.enableTableAsync(tableName);
			boolean isDisabled = ha.isTableDisabled(tableName);
			if (isDisabled) {
				ha.deleteTable(tableName);
			}
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 修改表几个 1.暂停表 2.启动 此方法是异步的 无同步方法
	 */
	@Test
	public void modify() {
		String tableName = "test2";
		try {
			HBaseAdmin ha = new HBaseAdmin(conf);
			HTableDescriptor hd = ha.getTableDescriptor(TableName
					.valueOf(tableName));
			HColumnDescriptor hcd = new HColumnDescriptor(Bytes.toBytes("gooo"));
			hd.addFamily(hcd);
			hd.setMaxFileSize(1 << 20);
			ha.disableTable(tableName);
			ha.modifyTable(tableName, hd);
			ha.enableTable(tableName);
			HTableDescriptor tableDescriptor = ha.getTableDescriptor(Bytes
					.toBytes(tableName));
			System.out.println(tableDescriptor);
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
