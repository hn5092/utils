package com.x.hdfs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class HDFSDemo {

	FileSystem fs = null;
	
	@Before
	public void init() throws Exception{
		fs = FileSystem.get(new URI("hdfs://itcast01:9000"), new Configuration(), "root");
	}
	
	@Test
	public void testUpload() throws Exception{
		InputStream in = new FileInputStream("/root/install.log");
		OutputStream out = fs.create(new Path("/log123.log"));
		IOUtils.copyBytes(in, out, 1024, true);
	}
	
	@Test
	public void testMkdir() throws IllegalArgumentException, IOException{
		boolean  flag = fs.mkdirs(new Path("/a/aa"));
		System.out.println(flag);
	}
	
	@Test
	public void testDel() throws IllegalArgumentException, IOException{
		boolean flag = fs.delete(new Path("/a"), true);
		System.out.println(flag);
	}
	
	
	
	public static void main(String[] args) throws Exception {
		FileSystem fs = FileSystem.get(new URI("hdfs://xym01:9000"), new Configuration());
		InputStream in = fs.open(new Path("/jdk"));
		OutputStream out = new FileOutputStream("/home/jdk1.7.tar.gz");
		IOUtils.copyBytes(in, out, 4096, true);
		
	}
}
