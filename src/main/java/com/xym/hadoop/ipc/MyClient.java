package com.xym.hadoop.ipc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import org.apache.hadoop.net.NetUtils;

public class MyClient {
	public static void main(String[] args) {
		try {
			 Selector selector = Selector.open();
			 SocketChannel socketChannel1 = SocketChannel.open();
			 Socket socket = socketChannel1.socket();
			 socket.bind(new InetSocketAddress("localhost", 0));
			 OutputStream outStream = NetUtils.getOutputStream(socket);
			go();
			
			 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void go() {
		boolean isHeaderRead = false;
		while(true){
			
			if (!isHeaderRead ) {
				continue;
			}		
		}
	}
}
