package xym.hbase.api;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HbaseCount {
	private static final Log LOG = LogFactory.getLog(HbaseCount.class);
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
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "xym01:2181,xym02:2181,xym03:2181");
		try {
			scan = new Scan(Bytes.toBytes("1000"), Bytes.toBytes("1100"));
			table = new HTable(conf, "auto");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setAutoFlushTo(false);
	}
	
	/**
	 * 这里的计数器可以是随便的一个列族的列,但是这个列不能是已经存在的列,这里的列是区分大小写的
	 */
	@Test
	public void testOne(){
		try {
			long incrementColumnValue = table.incrementColumnValue(ROW1, INFO, HINT1, (long)1);
			System.out.println(incrementColumnValue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 使用多个计数器 多个计数器可以同时增加或者减少
	 */
	@Test
	public void testMulity(){
		Increment increment = new Increment(ROW1);
		increment.addColumn(INFO, HINT1, 1);
		increment.addColumn(INFO, HINT2, 1l);
		try {
			Result result = table.increment(increment);
			//keyvalues={111/info:hint1/1447051499211/Put/vlen=8/mvcc=0, 111/info:hint2/1447051499211/Put/vlen=8/mvcc=0}
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
