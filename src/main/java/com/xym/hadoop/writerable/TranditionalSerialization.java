package com.xym.hadoop.writerable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TranditionalSerialization {
  public static void main(String[] args) throws IOException {
    TestPeoPle testPeoPle = new TestPeoPle("xym");
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
    oos.writeObject(testPeoPle);
  }
}
