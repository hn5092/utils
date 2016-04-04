package com.xym.hadoop.thread;

public class TraditionalNotify {
  public static void main(String[] args) {
    new Thread(new Runnable() {
      
      public void run() {
        try {
          Thread.currentThread().wait();
          System.out.println("启动1");
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }).start();
    new Thread(new Runnable() {
      
      public void run() {
        try {
          Thread.currentThread().wait();
          System.out.println("启动2");
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }).start();

   
  }
}
