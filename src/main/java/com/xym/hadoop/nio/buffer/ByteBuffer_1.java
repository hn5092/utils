package com.xym.hadoop.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class ByteBuffer_1 {
  public static void main(String[] args) {
    //  使用参数创建的缓冲区 仍然可以使用全部的数组的容量 ,这里只是起始位置的参数  可以使用clear()方法    达到重写整个缓冲区的效果
    //position 的值为1,limit的值为3    ByteBuffer buffer = ByteBuffer.wrap(new byte[100],1,2); 

   ByteBuffer buffer = ByteBuffer.wrap(new byte[100]);
   //填充 java.nio.HeapByteBuffer[pos=2 lim=100 cap=100]
   buffer.put(0,(byte)'w').put((byte)'e').put((byte)'a').put((byte)'1').put((byte)'a').put((byte)'a').put((byte)'a');
   //翻转 java.nio.HeapByteBuffer[pos=0 lim=2 cap=100]
   buffer.flip();
   //判断是否存在 ture
   System.out.println(buffer.hasRemaining());
   //释放java.nio.HeapByteBuffer[pos=1 lim=2 cap=100]
   buffer.get();
   //压缩   此时postion的位置 就是你读取的位置. 前面的全部被舍去java.nio.HeapByteBuffer[pos=1 lim=6 cap=100]
   buffer.compact();
   //使用reset的时候 如果标记没有被定义的话会导致异常   java.nio.HeapByteBuffer[pos=5 lim=100 cap=100]
   buffer.position(3).mark().position(5);
   //java.nio.HeapByteBuffer[pos=3 lim=100 cap=100]
   buffer.reset();
   byte[] by = new byte[3];
   by[0] = '1';
   by[1] = '2';
   by[2] = '3';
   //java.nio.HeapByteBuffer[pos=6 lim=100 cap=100]
   // offset 从数组的哪个位置开始   length 开始之后的多少长度
   buffer.put(by,0,2);
   byte[] getByte = new byte[2];
   //java.nio.HeapByteBuffer[pos=0 lim=5 cap=100]
   buffer.flip();
   //offerset 从数组的哪个位置开始 length 开始之后的多少长度  如果需要取出的话需要 flipjava.nio.HeapByteBuffer[pos=1 lim=5 cap=100]
   buffer.get(getByte,0,1);
   //显示此时还有多少元素
   System.out.println(buffer.remaining());
   
   System.out.println(buffer.toString());
   //可以查看这个缓冲区是否有一个可存取的备份数组
   //使用allocate和wrap创建的数组都是间接地使用slice()的
   boolean hasa = buffer.hasArray();
   System.out.println(hasa);
   //返回一个比特数组
   byte[] array = buffer.array();
   //使用wrap创建的数组 得到的会是0  如果切分其他缓冲区的话就会得到不同的偏移量
   int arrayOffset = buffer.arrayOffset();
   System.out.println("位移是:"+arrayOffset);
   CharBuffer charBuffer =CharBuffer.wrap("hello!");
   //-------------------将缓冲区内剩余数据
   byte[] bigArray = new byte[100];
   int length = buffer.remaining();
   //如果获取的数量太多的话的 会导致异常   所以这里选择有的数量
   buffer.get(bigArray,0,length);
   
  }
}
  