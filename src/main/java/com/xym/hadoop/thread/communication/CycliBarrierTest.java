package com.xym.hadoop.thread.communication;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CycliBarrierTest {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		//表示同时有几个线程达到的时候才能执行下一步
		final CyclicBarrier cBarrier = new CyclicBarrier(3);
		for(int i = 0; i < 3; i++){
			Runnable runnable = new Runnable() {
				
				public void run() {
					try {
						Thread.sleep((long) (Math.random()*10000));
						System.out.println("线程"+Thread.currentThread().getName()+"即将到达集合点1,当前已经有"+(cBarrier.getNumberWaiting()+1)+"个小朋友到达目的地");
						cBarrier.await();
						Thread.sleep((long) (Math.random()*10000));
						System.out.println("线程"+Thread.currentThread().getName()+"集合完毕我们出发前往集合点2,当前已经有"+(cBarrier.getNumberWaiting()+1)+"个小朋友出发了");
						cBarrier.await();
						Thread.sleep((long) (Math.random()*10000));
						System.out.println("线程"+Thread.currentThread().getName()+"即将到达集合点2,当前已经有"+(cBarrier.getNumberWaiting()+1)+"个小朋友到达目的地");
						cBarrier.await();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			};
			executorService.execute(runnable);
		}
	}
}
