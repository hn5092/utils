package com.xym.hadoop.nio.channel;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLock_ {
  public static void main(String[] args) throws Exception {
    //fileLocj
    RandomAccessFile file = new RandomAccessFile("e:\\test.txt", "rw");
    FileChannel fileChannel = file.getChannel();
//    FileLock lock1 = fileChannel.lock();
    FileLock lock2 = fileChannel.lock(0L, Long.MAX_VALUE, true);
//    FileLock lock3 = fileChannel.tryLock(0L, Long.MAX_VALUE, true);
//    System.out.println(lock1.isShared());
    System.out.println(lock2.isShared());
//    System.out.println(lock1.isValid());
    System.out.println(lock2.isValid());
    lock2.overlaps(10, 20);
    lock2.release();
    System.out.println(lock2.isValid());
  }
}
