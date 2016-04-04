package com.xym.hadoop.thread;

public class Tradition {
  public static void main(String[] args) {
    Thread thread = new Thread() {
      @Override
      public void run() {
        while (true) {
          System.out.println("iam  run" + Thread.currentThread());
          System.out.println(this.getName());
          try {
            Thread.sleep(500);

          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    };
    thread.start();

    Thread thread2 = new Thread(new Runnable() {
      public void run() {
        while (true) {
          System.out.println("iam  run" + Thread.currentThread());
          System.out.println(Thread.currentThread().getName());
          try {
            Thread.sleep(500);

          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    });
    thread2.start();
      System.out.println("OK");
    new Thread(new Runnable() {
      
      public void run() {
        while(true){
          System.out.println("iam  runeeeee"+Thread.currentThread());
          System.out.println(Thread.currentThread().getName());
          try {
            Thread.sleep(500);
            
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          }
        
      }
    }) {
      public void run() {
        while (true) {
          System.out.println("iam  runaaaaaaaaa" + Thread.currentThread());
          System.out.println(this.getName());
          try {
            Thread.sleep(500);

          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      };
    }.start();
  }
}
