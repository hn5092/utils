package com.xym.hadoop.nio.channel;

import java.io.IOException;
import java.net.DatagramSocket;
import java.nio.channels.DatagramChannel;

public class DatagramChannelDemo {
  public static void main(String[] args) throws IOException {
   DatagramChannel channel = DatagramChannel.open();
   DatagramSocket socket = channel.socket();
   //每一个datagram都是一个子包含的尸体 有用他自己的目的地址不依赖其他数据报 
   //他是无连接的 可以发送单独的数据报给不同的目的地址,也可以接受惹你地址的数据包
   //
  }
}
