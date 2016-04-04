package com.xym.hadoop.writerable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.file.tfile.RawComparable;

public class TranditionalHadoopSerialization {
  public static void main(String[] args) throws IOException {
    TestPeoPle people = new  TestPeoPle("xym");
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    //需要该对象实现writable接口才可以进行操作  
    people.write(oos);
    RawComparable comparable = (RawComparable) WritableComparator.get(IntWritable.class);
  }
}
