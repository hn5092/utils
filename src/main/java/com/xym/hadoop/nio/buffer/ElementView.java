package com.xym.hadoop.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ElementView {
  public static void main(String[] args) {
    ByteBuffer buffer = ByteBuffer.allocate(12);
    //buffer提供了直接存取各种数据类型的API
    buffer.putInt(10);
    buffer.putInt(22);
    buffer.flip();
    //buffer也提供能可以直接读取数据的API 每4位可以读取一次
    System.out.println(buffer.getInt());
    int value = buffer.order(ByteOrder.BIG_ENDIAN).getInt();
    System.out.println(value);
    System.out.println(buffer.toString());
  }
}
