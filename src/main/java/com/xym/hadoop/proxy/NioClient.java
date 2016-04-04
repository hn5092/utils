package com.xym.hadoop.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioClient {
  private Selector selector;
  public void initClient(String ip, int port) throws IOException{
    //获得一个通道
    SocketChannel socketChannel = SocketChannel.open();
    //设置通道为非阻塞
    socketChannel.configureBlocking(false);
    //获得一个通道管理器
    this.selector = Selector.open();
    //客户端连接服务器,其实方法执行并没有实现连接,需要在Listren()方法中调用channel.finalsh
    socketChannel.connect(new InetSocketAddress(port));
    //将通道管理器和该通道绑定,并为该通道注册SelectionKey.OP_CONNECT事件
    socketChannel.register(selector, SelectionKey.OP_CONNECT);
  }
   /**
    * 采取轮询的方式监听selector上是否有需要处理的时间,如果有,则进行处理 
   * @throws IOException 
    */
  public void listen () throws IOException{
    //轮询访问selector
    while(true){
      selector.select();
      Iterator iterator = this.selector.selectedKeys().iterator();
      while(iterator.hasNext()){
        SelectionKey key = (SelectionKey)iterator.next();
        iterator.remove();
        if(key.isConnectable()){
          SocketChannel socketChannel = (SocketChannel)key.channel();
          if(socketChannel.isConnectionPending()){
            socketChannel.finishConnect();
          }
          
          socketChannel.configureBlocking(false);
          
          socketChannel.write(ByteBuffer.wrap(new String("i am client").getBytes()));
          socketChannel.register(selector, SelectionKey.OP_READ);
        }else if(key.isReadable()){
          read(key);
          
        }
      }
    }
  }
  public void read(SelectionKey key) throws IOException {
    SocketChannel socketChannel = (SocketChannel)key.channel();
    //创建读取缓冲区
    ByteBuffer buffer = ByteBuffer.allocate(10);
    socketChannel.read(buffer);
    byte[] data = buffer.array();
    String msg = new String(data).trim();
    System.out.println("客户端收到信息"+msg);
    ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
    socketChannel.write(outBuffer);
  }
  public static void main(String[] args) throws IOException {
    NioClient nioClient = new NioClient();
    nioClient.initClient("localhost", 6666);
    nioClient.listen();
  }
}


