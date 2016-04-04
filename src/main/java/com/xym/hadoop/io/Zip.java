package com.xym.hadoop.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

public class Zip {
public static void main(String[] args) throws Exception {
  System.out.println("xym come no");
  Class<?> codecClass = Class.forName("gunzip");
  Configuration configuration = new Configuration();
  CompressionCodec codec = (CompressionCodec)ReflectionUtils.newInstance(codecClass, configuration);
  CompressionOutputStream out = codec.createOutputStream(System.out);
  IOUtils.copyBytes(System.in, out, 4096,false);
  out.flush();
}
}
