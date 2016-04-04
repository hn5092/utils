package com.xym.hadoop.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.jcraft.jsch.Channel;


public class NioServer {
  //通道管理器
  private Selector selector;
  //初始化服务
  public void initService(int port) throws IOException{
    //打开一个通道
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    //江通道设置为非阻塞
    serverSocketChannel.configureBlocking(false);
    //绑定一个端口
    serverSocketChannel.socket().bind(new InetSocketAddress(port));
    //  获得一个管理器
    this.selector = Selector.open();
    //将通道管理器和钙通道绑定,并为该通道注册OP_ACCEPT事件,注册该事件后
    //当该事件到达时,selector.select()会返回,如果该事件没到达selector.select()会一直阻塞
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
  }
  
  /**
   * 采用轮询的方式监听selector上是否需有需要处理的而时间,如果有则进行处理
   */
  public void listen() throws IOException {
    System.out.println("服务器启动成功");
    //轮询访问selector
    while(true){
      //当注册的时间到达,方法返回,否则该方法会一直阻塞
      selector.select();
      Iterator iterator = this.selector.selectedKeys().iterator();
      while(iterator.hasNext()){
        //得到下一个selectionkey 
        SelectionKey key = (SelectionKey)iterator.next();
        //删除已经选中的key 防止重复出路
        iterator.remove();
        if(key.isAcceptable()){
          ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
          SocketChannel socketChannel = serverSocketChannel.accept();
          //设置成费阻塞
          socketChannel.configureBlocking(false);
          socketChannel.write(ByteBuffer.wrap(new String("hell i am server").getBytes()));
          socketChannel.register(this.selector, SelectionKey.OP_READ);
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
    System.out.println("服务端收到信息"+msg);
    ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
    socketChannel.write(outBuffer);
  }
  public static void main(String[] args) throws IOException {
    NioServer nioServer = new NioServer();
    nioServer.initService(6666);
    nioServer.listen();
    
  }
}
