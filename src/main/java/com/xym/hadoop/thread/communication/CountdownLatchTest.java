package com.xym.hadoop.thread.communication;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchTest{
	public static void main(String[] args) {
		//可以实现多个人通知一个人和一个人通知多个人
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final CountDownLatch countDownLatch2 = new CountDownLatch(2);
		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int i = 0; i < 2; i++){
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						System.out.println("苦苦等待");
						countDownLatch.await();
						countDownLatch2.countDown();
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			executorService.execute(runnable);
		}
		try {
			countDownLatch.countDown();
			countDownLatch2.await();
			System.out.println("终于等到了");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}