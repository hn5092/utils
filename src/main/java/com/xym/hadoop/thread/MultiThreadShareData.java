package com.xym.hadoop.thread;


public class MultiThreadShareData {
  static  ShareData shareData = new ShareData();
  public static void main(String[] args) {
    new Thread(new MyCount1(shareData)).start();
    new Thread(new MyCount2(shareData)).start();
    new Thread(new Runnable() {

      public void run() {
        shareData.decrement();
      }
    }).start();
    new Thread(new Runnable() {

      public void run() {
        shareData.increment();
      }
    }).start();

  }

}


class MyCount1 implements Runnable {
  ShareData shareData = null;

  public MyCount1(ShareData shareData) {
    this.shareData = shareData;
  }

  public void run() {
    shareData.increment();
  }

}


class MyCount2 implements Runnable {
  ShareData shareData = null;

  public MyCount2(ShareData shareData) {
    this.shareData = shareData;
  }

  public void run() {
    shareData.decrement();
  }
}


class ShareData {
  int count = 100;

  public void increment() {
    count++;
  }

  public void decrement() {
    count--;
  }
}
