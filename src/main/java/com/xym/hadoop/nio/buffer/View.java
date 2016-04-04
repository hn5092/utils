package com.xym.hadoop.nio.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;


public class View {
  public static void main(String[] args) {
    ByteBuffer  buffer = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
    CharBuffer asCharBuffer = buffer.asCharBuffer();
    buffer.put(0,(byte)0);
    buffer.put(1,(byte)'H');
    buffer.put(2,(byte)0);
    buffer.put(3,(byte)'I');
    buffer.put(4,(byte)0);
    buffer.put(5,(byte)'!');
    buffer.put(6,(byte)0);
    buffer.put(7,(byte)'!');
    buffer.put(0,(byte)0);
    println(buffer);
    println(asCharBuffer);
  }
  
  public static void println (Buffer buffer){
   System.out.println("postion: "+buffer.position()+ ",limit: " + buffer.limit()+","+buffer.toString());
  }
}
