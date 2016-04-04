package com.xym.hadoop.nio.buffer;

import java.nio.ByteOrder;
import java.nio.CharBuffer;

public class Order {
  public static void main(String[] args) {
    //octet  规则是  8 字节
    CharBuffer charBuffer = CharBuffer.allocate(10);
    //返回默认的字节顺序  intel是小端排序 ByteBuffer 默认为BIG_ENDIAN
    System.out.println(ByteOrder.nativeOrder());
    //可以查看是否为一个直接缓冲区
    System.out.println(charBuffer.isDirect());
  }
}
