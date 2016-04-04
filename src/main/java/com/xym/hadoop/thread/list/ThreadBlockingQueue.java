package com.xym.hadoop.thread.list;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 使用2个单个队列实现现成通讯同步通知
 * @author imad
 *
 */
public class ThreadBlockingQueue {

	public static void main(String[] args) throws InterruptedException {
		//阻塞队列  参考文档  
		final ThreadBlockingQueue.Bussiness bussiness = new ThreadBlockingQueue.Bussiness();
		Map<String, String> synchronizedMap = Collections.synchronizedMap(new HashMap<String,String >());
		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i <= 50; i++) {
					// synchronized (TraditionalThreadCommunication.class) {

					try {
						bussiness.sub(i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// }
				}
			}
		}).start();

		for (int i = 0; i <= 50; i++) {
			// synchronized (TraditionalThreadCommunication.class) {
			// }
			bussiness.main(i);	
		}
	}

	static class Bussiness {
		//使用单个队列进行
		final BlockingQueue<Integer> b1 = new ArrayBlockingQueue<Integer>(1);
		final BlockingQueue<Integer> b2 = new ArrayBlockingQueue<Integer>(1);
		/**
		 * 匿名构造方法   在构造方法之前执行 静态代码块是在加载类的时候执行
		 */
		{
		try {
			b2.put(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		}

		public void sub(int i) throws InterruptedException {
			
			b1.put(1);
			
				for (int j = 0; j <= 50; j++) {
					System.out.println("sub thread" + i + "loop" + j);
				}
			b2.take();

		}

		public void main(int i) throws InterruptedException {
			//阻塞了. 等待take
			b2.put(1);
				for (int j = 0; j <= 100; j++) {
					System.out.println("main thread" + i + "loop" + j);
				}
			b1.take();
		}
	}
	
	
}
