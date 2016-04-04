package xym.hbase.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

public class MyCoprocessor extends BaseRegionObserver {
	public static final byte[] TABLE_NAME = Bytes.toBytes("auto");
	public static final byte[] INFO_FAM = Bytes.toBytes("info");
	public static final byte[] ACTION_COL = Bytes.toBytes("action");
	public static final byte[] fromURL_COL = Bytes.toBytes("fromURL");
	public static final byte[] IP_COL = Bytes.toBytes("ip");
	public static final byte[] SCREEN_COL = Bytes.toBytes("screen");
	private static final byte[] ROW1 = Bytes.toBytes("111");
	private static final byte[] HINT1 = Bytes.toBytes("hint1");
	private static final byte[] HINT2 = Bytes.toBytes("hint2");
	HConnection hConnection;
	HTableInterface table;

	/**
	 * start打开一个表
	 */
	@Override
	public void start(CoprocessorEnvironment e) throws IOException {
		hConnection = HConnectionManager.createConnection(e.getConfiguration());
		table = hConnection.getTable(TABLE_NAME);
	}

	@Override
	public void stop(CoprocessorEnvironment e) throws IOException {
		hConnection.close();
	}

	@Override
	public void postGetOp(ObserverContext<RegionCoprocessorEnvironment> e,
			Get get, List<Cell> results) throws IOException {
		e.getEnvironment().getRegion().getRegionInfo().getTable();
		super.postGetOp(e, get, results);
		long incrementColumnValue = table.incrementColumnValue(ROW1, INFO_FAM, HINT1, (long)1);
		System.out.println(incrementColumnValue);
		
		
		/**
		 * 测试.............多加一行
		 */
//		Get get2 = new Get(Bytes.toBytes("100"));
//		Result result = table.get(get2);
//		Cell[] rawCells = result.rawCells();
//		List<KeyValue> kvs = new ArrayList<KeyValue>(results.size());
//		for (Cell c : rawCells) {
//			kvs.add(KeyValueUtil.ensureKeyValue(c));
//		}
//		// 如果不是这个表要尽快返回
//		results.clear();
//		postGet(e, get, kvs);
//		results.addAll(kvs);

	}

	@Test
	public void test() {
		Get p = new Get(Bytes.toBytes("100"));
//		System.out.println(new String(p.getRow()));
//		System.out.println(new String(p.getRow()).equals("100"));
		List<? extends Number> foo3 = new ArrayList<Integer>(); 
		System.out.println(foo3 instanceof Number);
//		List<? extends Number> foo31 = new ArrayList<? extends Integer>();	
//		 
//		// Double extends Number
//		 
//		List<? extends Number> foo32 = new ArrayList<? extends Double>();
		// Integer is a "superclass" of Integer (in this context)
		 
		List<? super Number> foo33 = new ArrayList<Object>();
		// Number is a superclass of Integer
		 
		List<? super Integer> foo34 = new ArrayList<Number>();
		// Object is a superclass of Integer
		 
		List<? super Integer> foo35 = new ArrayList<Object>();
		foo33.add(new Integer(1));
	}

}