package xym.hbase.api;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;





public class LearnHbaseApi {
	private static final Log LOG = LogFactory.getLog(LearnHbaseApi.class);
	private static final byte[] INFO = Bytes.toBytes("info");
	private static final byte[] URL = Bytes.toBytes("URL");
	private static final byte[] ROW1 = Bytes.toBytes("1111");
	private static final byte[] ROW2 = Bytes.toBytes("2222");
	private static final byte[] SCREEN = Bytes.toBytes("screen");

	Configuration conf;
	HTable table;
	@Before
	public void setup(){
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum",
				"xym01:2181,xym02:2181,xym03:2181");
		try {
			table = new HTable(conf, "test8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setAutoFlushTo(false);
	}
	
	
	@Test
	public void testPut(){
		Put put = new Put(Bytes.toBytes("a"));
		put.add(INFO, Bytes.toBytes("test"), Bytes.toBytes("1111"));
		try {
			table.put(put);
			table.flushCommits();
		} catch (RetriesExhaustedWithDetailsException | InterruptedIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test1() throws RetriesExhaustedWithDetailsException, InterruptedIOException{
		table.setAutoFlushTo(false);//取消自动推送
		//hbase.client.write.buffer     BYTE 单位
		table.flushCommits();//提交缓冲区的内容    遇到错误的行数是等整个缓冲区缓都缓冲完毕才会返回的
		// 每次调用put 和     setwritebuffersize()就会触发 提交操作那个
		Get get = new Get(Bytes.toBytes("111"));
//		get.addColumn(Bytes.toBytes("info"), Bytes.toBytes("URL"));
		try {
			Result res = table.get(get);
			byte[] value = res.getValue(Bytes.toBytes("info"), Bytes.toBytes("ip"));
			System.out.println(Bytes.toString(value));
			byte[] value2 = res.value();	//返回第一行的
			System.out.println("111111");
			System.out.println(Bytes.toString(value2));
			byte[] value3 = res.getRow();  // 返回行健
			System.out.println(Bytes.toString(value3));
			Cell[] rawCells = res.rawCells(); //返回所有信息
			for(Cell c : rawCells){
				System.out.println("this is  cell");
				System.out.println(new String(CellUtil.cloneRow(c)));
				System.out.println(new String(c.getValueArray(), "UTF-8"));
			}
			
			List<Cell> columnCells = res.getColumnCells(Bytes.toBytes("info"), Bytes.toBytes("ip"));
			for(Cell c : columnCells){
				
				System.out.println(new String(CellUtil.cloneValue(c)));
				System.out.println();
			}
			//检查某列是否包含
			System.out.println(res.containsColumn(Bytes.toBytes("info"), Bytes.toBytes("ips")));
//			NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = res.getMap();
			table.exists(get); //是否存在 这个列
			//检查存在 
			Result rowOrBefore = table.getRowOrBefore(Bytes.toBytes("111"), Bytes.toBytes("info"));
//			Delete delete = new Delete(row)
			//delete.delete () 最后参数是版本号
			//table.delete()
		} catch (IOException e) {
			// TODO Auto-generated catch  block
			e.printStackTrace();
		}
	}
	@Test
	public void testGet(){
		Get get = new Get(Bytes.toBytes("100"));
		Result result;
		try {
			result = table.get(get);
			for(Cell c:result.rawCells()){
				System.out.println(new String(CellUtil.cloneValue(c)));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void batch(){
		Put p1 = new Put(Bytes.toBytes("xym"));
		p1.add(INFO, URL, Bytes.toBytes("www.xym.com"));
		List<Row> batch = new ArrayList<Row>();
		batch.add(p1);
//		Get g = new Get(Bytes.toBytes("xym"));
//		batch.add(g);
//		Get g2 = new Get(Bytes.toBytes("xym"));
////		g.addFamily(Bytes.toBytes("xym"));  //没有的行就报错
//		batch.add(g2);
		try {
			Object[] result = new Object[batch.size()];
			table.batch(batch,result);
			Object[] batch2 = table.batch(batch);//出错就不能返回
			for(Object o:result){
				System.out.println(o);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 */
	@Test
	public void testScan(){
		Scan scan = new Scan(Bytes.toBytes("100000"), Bytes.toBytes("100001"));
		ResultScanner scanner = null;
		try {
			scan.setBatch(2);  //colum 
			scan.setCaching(10);//rpc times
//			scan.addColumn(INFO, SCREEN);
			 scanner = table.getScanner(scan);
			for(Result r:scanner){
			Cell[] rawCells = r.rawCells();
			System.out.println(rawCells.length+"this is long");
			for(Cell c : rawCells){
				System.out.println(new String(CellUtil.cloneValue(c)));
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			scanner.close();
		}
	}
	@Test
	public void testCache(){
		try {
			HTableDescriptor tableDescriptor = table.getTableDescriptor();
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
