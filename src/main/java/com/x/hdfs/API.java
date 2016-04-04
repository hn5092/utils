package com.x.hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.RunJar;
import org.junit.Test;

public class API {
	FileSystem fileSystem;
	@Test
	public void testGet() {
		Configuration configuration = new Configuration();
		configuration.set("fs.defaultFS", "hdfs://ytocluster");
		configuration.set("dfs.nameservices", "ytocluster");
		configuration.set("dfs.ha.namenodes.ytocluster", "nn1,nn2");
		configuration.set("dfs.namenode.rpc-address.ytocluster.nn1",
				"namenode01:8020");
		configuration.set("dfs.namenode.rpc-address.ytocluster.nn2",
				"hadoop-name:8020");
		configuration
				.set("dfs.client.failover.proxy.provider.ytocluster",
						"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");

		try {
			 fileSystem = FileSystem.get(configuration);
			FSDataInputStream open = fileSystem.open(new Path(
					"/T_EXP_OP_RECORD_TRUCK/T_EXP_OP_RECORD_TRUCK_20151209"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Test
	public void testDel() throws IllegalArgumentException, IOException{
		boolean flag = fileSystem.delete(new Path("/test/xym/hadoop/1"), true);
		System.out.println(flag);
	}
//	@Test
//	public void testGet() {
//		Configuration configuration = new Configuration();
//		configuration.set("fs.defaultFS", "hdfs://ns1");
//		configuration.set("dfs.nameservices", "ns1");
//		configuration.set("dfs.ha.namenodes.ns1", "nn1,nn2");
//		configuration.set("dfs.namenode.rpc-address.ns1.nn1",
//				"192.168.80.101:9000");
//		configuration.set("dfs.namenode.rpc-address.ns1.nn2",
//				"192.168.80.102:9000");
//		configuration
//				.set("dfs.client.failover.proxy.provider.ns1",
//						"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
//
//		try {
//			FileSystem fileSystem = FileSystem.get(configuration);
//			FSDataInputStream open = fileSystem.open(new Path(
//					"/cc/data/1/20140814"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}
