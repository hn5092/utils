package com.xym.hadoop.thread.executors;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class CallableAndFuture {
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		ExecutorService threadPool3 = Executors.newCachedThreadPool();
		Future<?> future2 = (Future<?>) threadPool3.submit(new Runnable() {
			
			@Override
			public void run() {
				
			}
		});
		 try {
			Object x = future2.get();
		} catch (InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		Future<String> future = threadPool.submit(new Callable<String>() {
			public String call() throws Exception {
				Thread.sleep(1000);
				return "hello";
			}
		});
		System.out.println("拿到结果");
		try {
			try {
				System.out.println("拿到结果" + future.get(100,TimeUnit.SECONDS));
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExecutorService threadPool2 = Executors.newFixedThreadPool(10);

		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool2);
		for (int i = 0; i < 10; i++){
			final int seq = i;
		completionService.submit(new Callable<Integer>() {

			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(new Random().nextInt(5000));
				return seq;
			}
		});
		}
		for(int i = 0; i < 10; i ++){
			try {
				System.out.println(completionService.take().get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
