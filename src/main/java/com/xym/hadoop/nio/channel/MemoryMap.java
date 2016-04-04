package com.xym.hadoop.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class MemoryMap {
  public static void main(String[] args) throws IOException {
    RandomAccessFile raf = new RandomAccessFile("e:\\test.txt","rw");
    FileChannel channel = raf.getChannel();
    MappedByteBuffer map = channel.map(MapMode.READ_ONLY, 0,raf.length());
    Thread.currentThread();
    map.load();
    //判断整个文件是否全部已经载入了内存
    boolean isLoad = map.isLoaded();
    System.out.println(isLoad);
    //将更改应用到磁盘上面 映射值对writer的有用 其余的是没用的
    map.force();
  }
}
