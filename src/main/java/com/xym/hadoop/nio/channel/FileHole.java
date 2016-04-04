package com.xym.hadoop.nio.channel;


import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileHole {
  public static void main(String[] args) throws Exception {
    RandomAccessFile randomAccessFile = new RandomAccessFile("e:\\test.txt", "rw");
    randomAccessFile.seek(1000);
    FileChannel channel = randomAccessFile.getChannel();
    //内存映射MapMode.PRIVATE 表示您想要一个写时拷贝（copy-on-write）的映射 
    ByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, 100);
    System.out.println(channel.position());
    randomAccessFile.seek(500);
    System.out.println(channel.position());
    //改变文件的size
    channel.truncate(100);
    //force 属性可以将全部待定的修改应用到磁盘文件上    false表示值修改文件数据
    channel.force(true);
  }
  
}
