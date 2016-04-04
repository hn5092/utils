package com.xym.hadoop.thread.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
  public static void main(String[] args) {
//    ExecutorService threadPool = Executors.newFixedThreadPool(3);
//    ExecutorService threadPool = Executors.newCachedThreadPool();
    //线程死了重新启动
    ExecutorService threadPool = Executors.newSingleThreadExecutor();
    for (int i = 0; i < 15; i++) {
      final int index = i;
      threadPool.execute(new Runnable() {

        public void run() {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          System.out.println(Thread.currentThread().getName() + " this loop of " + index);
        }
      });
    }
    threadPool.shutdown();
    
    //
     Executors.newScheduledThreadPool(3).schedule( new Runnable() {
      public void run() {
        System.out.println("boom");
      }
    }, 10,TimeUnit.SECONDS);
    
    
    
    
  }
}
