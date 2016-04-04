package com.xym.hadoop.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import com.jcraft.jsch.Buffer;

public class ChanneclCopy {
  public static void main(String[] args) throws IOException {
    ReadableByteChannel source = Channels.newChannel(System.in);
    WritableByteChannel dest = Channels.newChannel(System.out);
    System.out.println(source.isOpen());
    Thread t = Thread.currentThread();
    System.out.println(t.isInterrupted());
    channelCopy1(source,dest);
    channelCopy2(source,dest);
  }

  private static void channelCopy2(ReadableByteChannel source, WritableByteChannel dest) throws IOException {
    ByteBuffer buff = ByteBuffer.allocate(16*1024);
    while(source.read(buff) != -1){
      buff.flip();
      while (buff.hasRemaining( )) {
        dest.write (buff);
        }
      buff.clear( );
    }
  }

  private static void channelCopy1(ReadableByteChannel source, WritableByteChannel dest) throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(16*1024);
    while(source.read(buffer) != -1){
      buffer.flip();
      dest.write(buffer);
      buffer.compact();
    }
    buffer.flip();
    while(buffer.hasRemaining()){
      dest.write(buffer);
    }
  }
  
  
}
