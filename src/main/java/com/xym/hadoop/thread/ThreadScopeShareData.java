package com.xym.hadoop.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class ThreadScopeShareData {
  private static Map<Thread,Integer> map = new HashMap<Thread,Integer>();
  public static void main(String[] args) {
    for(int i=0; i<2; i++){
      new Thread(new Runnable() {
        public void run() {
          int data = new Random().nextInt();
          System.out.println("这是data值"+data);
          System.out.println("这是当前线程"+Thread.currentThread().getName());
          synchronized (map) {//如果不加锁的话 会同时去写 导致线程安全问题
            
            map.put(Thread.currentThread(), data);
          }
//          System.out.println(Thread.currentThread().getName()+ "get" + data );
          System.out.println("这是map值"+map.get(Thread.currentThread()));
          new A().get();
          new B().get();
        }
      }).start();
    }
  }
  static class A{
    public int get(){
      System.out.println("A"+Thread.currentThread().getName());
      int data = map.get(Thread.currentThread());
      System.out.println("A" + Thread.currentThread().getName()+ "get" + data );
      return data;
    }
  }
  static class B{
    public int get(){
      System.out.println("B"+Thread.currentThread().getName());

      int data = map.get(Thread.currentThread());
      System.out.println("B" + Thread.currentThread().getName()+ "get" + data );

      return data;
    }
  }
}
