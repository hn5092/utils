package com.xym.hadoop.nio.buffer;

import java.nio.CharBuffer;

public class CopyBuffer {
   public static void main(String[] args) {
      CharBuffer buffer = CharBuffer.allocate(8);
      Utils.println(buffer.limit());
      Utils.println(buffer.position());
      buffer.position(3).limit(6).mark().position(5);
      Utils.println(buffer);
      //复制一个缓冲区
      CharBuffer dupeBuffer = buffer.duplicate();
      System.out.println(dupeBuffer.isReadOnly());
      
      //使用asreadonlybuffer()
      CharBuffer asReadOnlyBuffer = buffer.asReadOnlyBuffer();
      System.out.println(asReadOnlyBuffer.isReadOnly());
      //从原始数据的当前位置复制到 最后一个元素的位置(limit)
      buffer.position(2).limit(5);
      CharBuffer slice = buffer.slice();
      System.out.println("this is slicet propotise position: "+slice.position()+" ,limit: "+slice.limit());
      
   }
}
