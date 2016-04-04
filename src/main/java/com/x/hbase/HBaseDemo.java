package com.x.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;
public class HBaseDemo {
	Configuration conf;
	@Before
	public void init(){
		conf=HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum","xym04:2181,xym05:2181,xym02:2181");
	}
	
	
	@Test //insert row
	public void testPut() throws IOException{
		HTable table =new HTable(conf, "people1");
		for(int i = 0 ; i <10000;i++){
		Put put =new Put(Bytes.toBytes("kr000"+i));
		put.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("zhangsanfeng"+i));
		put.add(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes("111"+i));
		put.add(Bytes.toBytes("info"), Bytes.toBytes("money"), Bytes.toBytes(300000+i));
		table.put(put);
		}
		table.close();
		
	}
	
	
	@Test //select row
	public void testGet() throws IOException{
		HTable table=new HTable(conf, "auto");
		Get get=new Get(Bytes.toBytes("kr0001"));
		Result result = table.get(get);
		byte[] value = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("age"));
		System.out.println(Bytes.toString(value));
		table.close();
		
	}
	@Test
	public void testScan() throws IOException{
		HTable table =new HTable(conf,"people");
		Scan san=new Scan();//begin end 左闭右开
		ResultScanner scanner = table.getScanner(san);
		for(Result r:scanner){
			byte[] value = r.getValue(Bytes.toBytes("info"), Bytes.toBytes("age"));
			System.out.println(Bytes.toString(value));
		}
		table.close();
	}
	@Test
	public void testDelete() throws IOException{
		HTable table = new HTable(conf, "people");
		Delete delete = new Delete(Bytes.toBytes("kr0001"));
		table.delete(delete);
		table.close();
	}
	
	//create table
	public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		Configuration 	conf=HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum","xym04:2181,xym05:2181,xym02:2181");
		HBaseAdmin admin=new HBaseAdmin(conf);
		HTableDescriptor htd=new HTableDescriptor(TableName.valueOf("auto"));
		HColumnDescriptor hcDescriptor=new HColumnDescriptor("info");
		hcDescriptor.setMaxVersions(1);
		htd.addFamily(hcDescriptor);
		admin.createTable(htd);
		admin.close();
	}
}
