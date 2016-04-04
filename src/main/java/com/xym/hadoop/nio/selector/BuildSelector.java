package com.xym.hadoop.nio.selector;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import sun.nio.ch.SelChImpl;

public class BuildSelector {
  public static void main(String[] args) throws IOException {
    SocketChannel socketChannel1 = SocketChannel.open();
    SocketChannel socketChannel2 = SocketChannel.open();
    SocketChannel socketChannel3 = SocketChannel.open();
    socketChannel1.configureBlocking(false);
    socketChannel2.configureBlocking(false);
    socketChannel3.configureBlocking(false);
    Selector selector = Selector.open();
    SelectionKey selectionKey = socketChannel1.register(selector, SelectionKey.OP_READ);
    SelectionKey selectionKey2 = socketChannel2.register(selector, SelectionKey.OP_WRITE);
    SelectionKey selectionKey3 = socketChannel3.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    //select方法将线程置于睡眠状态,知道这些感兴趣的事情中的操作中的一个发生或者10秒过去了
    int select = selector.select(1);
    int interestSet = selectionKey.interestOps();
    //判断这个事件是否在集合里
    boolean isinteresterinAccept = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
    int read = selectionKey.readyOps();
    //监测什么时间或操作准备就绪  也可以用上面的方法去监测是否就绪
    selectionKey.isAcceptable();
    selectionKey.isConnectable();
    selectionKey.isReadable();
    selectionKey.isWritable();
    
    //如何通过key获得channel和selector
    Channel channel = selectionKey.channel();
    Selector selector2 = selectionKey.selector();
    //把一个对象的信息附着到上面   
    selectionKey.attach(new Object());
    //得到附着在上面的信息
    Object o = selectionKey.attachment();
    //还可以直接在注册的时候使用最后一个参数知己附加这个参数
    //SelectionKey key = channel.register(selector, SelectionKey.OP_READ, theObject);  
    
    //select 方法是阻塞的 直到返回你感兴趣的事件 返回值int是返回准备就绪的通道个数
    int select2 = selector.select();
    int timeout = 1000;
    //这个方法的不同的地方在于可以选择多久的超时时间
    int select3 = selector.select(timeout);
    //不管有什么通道准备都会立刻返回, 使用这个方法是不阻塞的  如果没有的话直接返回0
    int select4 = selector.selectNow();
    
    //如果select返回了值 表示已经有通道准备就绪啦,使用下面方法可以得到准备就绪的通道
    Set<SelectionKey> selectedKeys = selector.selectedKeys();
    Iterator<SelectionKey> selIterator = selectedKeys.iterator();
    while(selIterator.hasNext()){
      SelectionKey s = selIterator.next();
      if(s.isAcceptable()){
        System.out.println("jieshou");
      } else if (s.isConnectable()){
        System.out.println("连接了");
      } else if (s.isReadable()){
        System.out.println("准备读");
      } else if (s.isWritable()){
        System.out.println("写");
      }
      //切记用完之后一定要移除  下次通道就绪了selector会自动把这个通过再次加入集合之中
      selIterator.remove();
      //返回的通道类型需要自己转型才可以 
//      SelectableChannel channel2 = s.channel(); 
      
    }
    //用完了选择器之后 , 可以关闭选择器,关闭之后的选择器 所有的选择器上面的selectorkey 都将关闭
    selector.close();
    
    
    
  }
}
