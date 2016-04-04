package com.xym.hadoop.thread;

public class TraditionalThreadCommunication {
  //用到共同数据(包括同步锁)或共同算法的若干方法都应该鬼在同一个类身上,这种设计
  //正好体现了高类聚和程序健壮性
  // 对于线程里需要同步的  就放到一起
  public static void main(String[] args) throws InterruptedException {
    final Bussiness bussiness = new Bussiness();
    new Thread(new Runnable() {

      public void run() {
        for (int i = 0; i <= 50; i++) {
//          synchronized (TraditionalThreadCommunication.class) {
            
          try {
            bussiness.sub(i);
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
//          }
        }
      }
    }).start();

    for (int i = 0; i <= 50; i++) {
//      synchronized (TraditionalThreadCommunication.class) {
//      }
      bussiness.main(i);
    }
  }

}
class Bussiness{
  private boolean bShouldSub = true;
  public synchronized void sub(int i) throws InterruptedException{
    while(!bShouldSub){   //代码更健壮  防止伪唤醒
      this.wait();
      
    }
      for (int j = 0; j <= 50; j++) {
        System.out.println("sub thread" + i + "loop" + j);
      }
    
    bShouldSub = !bShouldSub;
    this.notify();
  }
  public synchronized void main(int i) throws InterruptedException{
    if(bShouldSub){
      this.wait();
    }
      for (int j = 0; j <= 100; j++) {
        System.out.println("main thread" + i + "loop" + j);
      } 
    bShouldSub = !bShouldSub;
    this.notify();
  }
}