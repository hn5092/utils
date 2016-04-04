package com.xym.hadoop.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

public class SocketChanelDemo {
  public static void main(String[] args) throws IOException {
    //socket 是面向流的
    SocketChannel channel = SocketChannel.open();
    channel.configureBlocking(false); // 设置成为为非阻塞模式
    Object lockobj = channel.blockingLock();
    // 上锁
    synchronized (lockobj) {
      boolean blocking = channel.isBlocking();
      System.out.println(blocking);
      channel.configureBlocking(false);
    }
    // 开始连接
    channel.connect(new InetSocketAddress("localhost", 80));
    // finishconnect方法用来完成连接过程
    // 1. 如果还没有connect的话 就会报错 noconnectionpendingexcept
    // 2.连接正在进行,尚未完成,此时会返回一个false 其他什么都不会发生
    // 在非阻塞模式下调用conncet方法后 socketchannel会被切换回阻塞模式,
    // 如果有必要 的话 调用的线程会阻塞直至连接建立完成, 接着finishconnect返回一个true
    // 3.初次调用connect或者最后一次调用finishchannel 连接建立过程已经完成,socketchannel对象状态更新到已连接
    // 连接已经建立的话 什么都不会发生
    channel.finishConnect();
    // 当连接状态在连接等待时候,可以 使用 finishconnect或者isconnectpending 或者 isconnected()
    // 异步方法
    while (!channel.finishConnect()) {
      // todo 判断 当没有连接好的时候做其他事情
    }
    // dosomethingwithchannel() 连接完成之后开始进行通道的相关操作
    // sc.close()
    // 调用此方法能得到一个socket对象 如果一个直接创建的socket对象没有连接那么getchannel只会返回null channel.socket();
    
    //任何一个读写操作 方法都会阻塞     只要操作在connect和finnishconnect的时候
    
  }
}
