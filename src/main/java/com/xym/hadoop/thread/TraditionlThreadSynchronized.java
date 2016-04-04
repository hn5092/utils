package com.xym.hadoop.thread;

public class TraditionlThreadSynchronized {
  public static void main(String[] args) {
    //静态方法不能new内部类对象
    new TraditionlThreadSynchronized().init();

  }
  private void init(){
    final Output output = new Output();
    new Thread(new Runnable() {
      public void run() {
       while(true){
         try {
          Thread.sleep(10);
          output.output("xym");
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
       }
      }
    }).start();
    new Thread(new Runnable() {
      public void run() {
       while(true){
         try {
          Thread.sleep(10);
          output.output3("xym2");
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
       }
      }
    }).start();
  }
  //对象要一样才能  上锁
  static class Output{
    String nam = "";
    public synchronized void output(String name){
      int len = name.length();
//      synchronized (name) { 不对 只能锁同一个对象
      //  synchronized (nam) { 也可以
      //synchronized (output.class) { 可以跟静态对象
//         synchronized (this) {
        for(int i=0;i<len;i++){
          System.out.print(name.charAt(i));
        }
//      }
    
      System.out.println();
    }
    public synchronized void output2(String name){
      int len = name.length();
//      synchronized (name) { 不对 只能锁同一个对象
      //  synchronized (nam) { 也可以
//         synchronized (this) {
        for(int i=0;i<len;i++){
          System.out.print(name.charAt(i));
        }
//      }
    
      System.out.println();
    }
    //
    public static synchronized void output3(String name){
      int len = name.length();
//      synchronized (name) { 不对 只能锁同一个对象
      //  synchronized (nam) { 也可以
//         synchronized (this) {
        for(int i=0;i<len;i++){
          System.out.print(name.charAt(i));
        }
//      }
    
      System.out.println();
    }
  }
}
