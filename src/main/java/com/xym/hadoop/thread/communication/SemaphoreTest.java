package com.xym.hadoop.thread.communication;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	public static void main(String[] args) {
		ExecutorService tp = Executors.newCachedThreadPool();
		//创建一个 实例表示同时有几个坑    只有一个坑可以用在单线程防止死亡
		final Semaphore semaphore = new Semaphore(1);
		for(int i = 0; i < 10; i++){
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						//得到一个坑
						semaphore.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("线程:"+Thread.currentThread().getName()+"进入,当前已有"+ (4-semaphore.availablePermits())+"灯");
					try {
						Thread.sleep(new Random().nextInt(10000));
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					//释放一个坑
					semaphore.release();
					System.out.println("线程:"+Thread.currentThread().getName()+"进入,当前已有"+ (4-semaphore.availablePermits())+"灯");
				}
			};
			tp.execute(runnable);
		}
	}
}
