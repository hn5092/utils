package hbase.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.filter.InclusiveStopFilter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

public class HbaseFitAPI {
	private static final Log LOG = LogFactory.getLog(HbaseFitAPI.class);
	private static final byte[] INFO = Bytes.toBytes("info");
	private static final byte[] URL = Bytes.toBytes("URL");
	private static final byte[] ROW1 = Bytes.toBytes("1111");
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
			scan = new Scan(Bytes.toBytes("163874"), Bytes.toBytes("164874"));
			table = new HTable(conf, "auto");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setAutoFlushTo(false);
	}

	/**
	 * 基于行健的来过滤的
	 */
	@Test
	public void testRowFilter() {

		scan.addColumn(INFO, SCREEN);
		Filter filter = new RowFilter(CompareOp.LESS_OR_EQUAL,
				new RegexStringComparator(""));
		// Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,new
		// RegexStringComparator(""))
		scan.setFilter(filter);
		try {
			ResultScanner scanner = table.getScanner(scan);
			for (Result res : scanner) {
				System.out.println(res);
			}
			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 列族过滤器和列名过滤器 可以精确找到列族列名
	 */
	@Test
	public void testFamily() {
		// 列族过滤器
		FamilyFilter familyFilter = new FamilyFilter(CompareOp.EQUAL,
				new BinaryComparator(INFO));
		// 列名过滤器
		QualifierFilter qualifierFilter = new QualifierFilter(CompareOp.EQUAL,
				new BinaryComparator(URL));
		// 得到相同的值的
		ValueFilter valueFilter = new ValueFilter(
				CompareOp.EQUAL,
				new BinaryComparator(
						Bytes.toBytes("http://auto.msn.com.cn/auto_international/20120407/1389813.shtml")));
		// ValueFilter valueFilter = new ValueFilter(CompareOp.EQUAL, new
		// SubstringComparator("http://auto.msn.com.cn/auto_international/20120407/1389813.shtml"));
		scan.setFilter(familyFilter);
		scan.setFilter(qualifierFilter);
		scan.setFilter(valueFilter);
		try {
			ResultScanner scanner = table.getScanner(scan);
			for (Result res : scanner) {
				System.out.println(res);
			}
			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查找特定列的特定的值
	 */
	@Test
	public void testSingleColumnValue() {
		// 查询包含的列
		SingleColumnValueFilter sFilter = new SingleColumnValueFilter(INFO,
				SCREEN, CompareOp.EQUAL, new SubstringComparator("1680x1050"));
		// 包含的列不会使用 SingleColumnValueExcludeFilter
		// 查询浅醉
		PrefixFilter pFilter = new PrefixFilter(Bytes.toBytes("1000"));
		scan.setFilter(sFilter);
		scan.setFilter(pFilter);
		getres();

	}

	/**
	 * 
	 * 
	 */
	@Test
	public void testPage() {
		Filter filter = new PageFilter(10);
		int totalRows = 0;
		byte[] lastRow = null;
		int page = 0;
		scan.setFilter(filter);
		while (true) {
			try {
				// //////这里是扫描了再拿值- .- 要放下去
				ResultScanner scanner = table.getScanner(scan);
				if (lastRow != null) {
					byte[] startRow = Bytes.add(lastRow, new byte[0]);
					page++;
					scan.setStartRow(startRow);
				}
				Result result;
				while ((result = scanner.next()) != null) {
					totalRows++;
					lastRow = result.getRow();
					System.out.println(result);
					System.out.println("this is page " + page + "total row "
							+ totalRows);
				}

				scanner.close();
				if (page == 3)
					break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testPage12() {
		Filter filter = new PageFilter(10);
		int totalRows = 0;
		byte[] lastRow = null;
		int page = 0;
		scan.setFilter(filter);
		Set<Result> set = new HashSet<Result>();
		while (true) {
			try {
				if (lastRow != null) {
					byte[] startRow = Bytes.add(lastRow, new byte[0]);
					page++;
					scan.setStartRow(startRow);
				}
				ResultScanner scanner = table.getScanner(scan);
				Result result;
				while ((result = scanner.next()) != null) {
					set.add(result);
					totalRows++;
					lastRow = result.getRow();
					System.out.println(result);
					System.out.println("this is page " + page + "total row "
							+ totalRows);
				}

				scanner.close();
				if (page == 3)
					break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(set.size());
	}

	/**
	 * 只返回他的列名 如果设置构造器的值为true的话 就返回原来的值的长度
	 */
	@Test
	public void keyOnlyFilter() {
		KeyOnlyFilter f = new KeyOnlyFilter();
		scan.setFilter(f);
		getres();
	}
	/**
	 * 只返回第一列 这个第一列
	 */
	@Test
	public void firstKeyOnly(){
		FirstKeyOnlyFilter f = new FirstKeyOnlyFilter();
		scan.setFilter(f);
		getres();
	}
	
	/**
	 * 扫描到结束的行
	 */
	@Test
	public void endRow(){
		InclusiveStopFilter isf = new InclusiveStopFilter(Bytes.toBytes("10002"));
		scan.setFilter(isf);
		getres();
	}
	/**
	 * 根据时间查找
	 */
	@Test
	public void Time(){
		List<Long> ls = new ArrayList<Long>();
		ls.add(1446775297235L);
//		TimestampsFilter tf = new TimestampsFilter(ls);
//		scan.setFilter(tf);
		scan.setTimeStamp(1446775297235L);
		getres();
	}
	
	@Test
	public void testMyFilter(){
//		scan.addColumn(INFO, SCREEN);
//		Filter myFilter = new org.apache.hadoop.hbase.filter.MyFilter();
//		scan.setFilter(myFilter);
		getres();
	}
	
	
	public void getres() {
		try {
			
			ResultScanner scanner = table.getScanner(scan);
			for (Result res : scanner) {
				System.out.println(res);
				Cell[] rawCells = res.rawCells();
				for(Cell c : rawCells){
					System.out.println(new String(CellUtil.cloneValue(c)));
				}
			}
			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
