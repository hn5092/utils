package com.xym.hadoop.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServiceSocketService {
  public static void main(String[] args) throws IOException {
    ServerSocketChannel channel = ServerSocketChannel.open();
    ServerSocket socket = channel.socket();
    socket.bind(new InetSocketAddress("localhost", 8888));
    SocketChannel accept = channel.accept();
    
  }
}
