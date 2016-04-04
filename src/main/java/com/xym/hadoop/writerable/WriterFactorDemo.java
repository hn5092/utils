package com.xym.hadoop.writerable;

import org.apache.hadoop.io.WritableFactories;

public class WriterFactorDemo {
  public static void main(String[] args) {
    TestPeoPle testPeoPle = (TestPeoPle) WritableFactories.newInstance(TestPeoPle.class);
    testPeoPle.sayhi();
    
  }
}
