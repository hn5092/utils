package com.xym.hadoop.writerable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableFactories;
import org.apache.hadoop.io.WritableFactory;

public class TestPeoPle implements Writable, Serializable{
  static{
    WritableFactories.setFactory(TestPeoPle.class, new WritableFactory() {
      
      public Writable newInstance() {
        return new TestPeoPle();
      }
    });
  }
  String name ;
  public TestPeoPle(String name) {
    this.name = name;
    
  }
  public TestPeoPle() {
    // TODO Auto-generated constructor stub
  }
  
  public void write(DataOutput out) throws IOException {
    out.writeUTF(name);
  }

  public void readFields(DataInput in) throws IOException {
    in.readUTF();
  }
  public void sayhi (){
    System.out.println("hello world");
  }

}
