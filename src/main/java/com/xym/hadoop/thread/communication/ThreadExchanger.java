package com.xym.hadoop.thread.communication;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExchanger {
	public static void main(String[] args) {
		//到达交换的时候 会等待. 直到另一个线程开始交换
		ExecutorService threadPools = Executors.newCachedThreadPool();
		final Exchanger<String> exchanger = new Exchanger<String>();
		threadPools.execute(new Runnable() {

			public void run() {
				String data1 = "i am frist";
				String exchangerdata1 = null;
				try {
					Thread.sleep((long) (Math.random()*10000));
					System.out.println("thread 1 exchanger data");
					exchangerdata1 = exchanger.exchange(data1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()
						+ "get data1 :" + exchangerdata1);
			}
		});
		threadPools.execute(new Runnable() {

			public void run() {
				String data2 = "i am two";
				String exchangerdata2 = null;
				try {
					Thread.sleep((long) (Math.random()*10000));
					System.out.println("thread 2 exchanger data");
					exchangerdata2 = exchanger.exchange(data2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()
						+ "get data1 :" + exchangerdata2);
			}
		});
	}

}
