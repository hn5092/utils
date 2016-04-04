package com.xym.hadoop.thread;

import java.util.Random;



public class ThreadLocalTest {
  static ThreadLocal<Integer> x = new ThreadLocal<Integer>();
//  static ThreadLocal<MyThreadSocpeData> x1 = new ThreadLocal<MyThreadSocpeData>();

  public static void main(String[] args) {
    
    for(int i=0; i<2; i++){
      new Thread(new Runnable() {
        public void run() {
          int data = new Random().nextInt();
          System.out.println("这是data值"+data);
          System.out.println("这是当前线程"+Thread.currentThread().getName());
//          x.set(data); 
//          System.out.println(Thread.currentThread().getName()+ "get" + data );
          //封装之后的
          Thread.currentThread().interrupt();
          MyThreadSocpeData.getInstance().setName("name"+data).setAge(data);
          new A().get();
          new B().get();
        }
      }).start();
    }
  }
  static class A{
    public void get(){
      String name = MyThreadSocpeData.getInstance().getName();
      int age = MyThreadSocpeData.getInstance().getAge();
      System.out.println("A" + Thread.currentThread().getName()+ "get name:" + name + "get age:" + age );
    }
  }
  static class B{
    public void get(){
      String name = MyThreadSocpeData.getInstance().getName();
      int age = MyThreadSocpeData.getInstance().getAge();
      System.out.println("B" + Thread.currentThread().getName()+ "get name:" + name + "get age:" + age );
    }
  }
}
class MyThreadSocpeData{
  private MyThreadSocpeData(){};
  //单例模式
//  public static synchronized MyThreadSocpeData getInstance(){
//    if(instance == null){
//      instance =  new MyThreadSocpeData();
//    }
////    return instance;//饥寒 不需要再来
//  }
  //不需要sync
  public static  MyThreadSocpeData getInstance(){
    MyThreadSocpeData instance = map.get();
    if(instance == null){
      instance =  new MyThreadSocpeData();
      map.set(instance);
    }
    return instance;
  }
//  private static MyThreadSocpeData instance = new MyThreadSocpeData();//饱了模式
//  private static MyThreadSocpeData instance = null;
  static ThreadLocal<MyThreadSocpeData> map = new ThreadLocal<MyThreadSocpeData>();

  
  private String name;
  private int age;
  public String getName() {
    return name;
  }
  public MyThreadSocpeData setName(String name) {
    this.name = name;
    return this;
  }
  public int getAge() {
    return age;
  }
  public MyThreadSocpeData setAge(int age) {
    this.age = age;
    return this;
  }
  
}
