package com.xym.hadoop.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test1 {
	public static void main(String[] args) {
		//1个也是可以的
		final BlockingQueue<String> b = new ArrayBlockingQueue<String>(4);
		System.out.println("begun"+System.currentTimeMillis()/1000);
		for(int i = 0; i < 4; i++){
			new Thread(new Runnable() {
				
				public void run() {
					while(true){
					try {
						parenLog(b.take());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
				}
			}).start();
			
		}
		
		//method 1
//		ExecutorService executorService = Executors.newFixedThreadPool(4);
		for(int i =0; i < 16; i++){
			final String log = ""+(i+1);
			{
				try {
					b.put(log);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//		method 1				
//				executorService.execute(new Runnable() {
//					
//					public void run() {
//						Test1.parenLog(log);
//					}
//				});
				
			}
		}
	}

	private static void parenLog(String log) {
		System.out.println(log + ":" + System.currentTimeMillis()/1000);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
