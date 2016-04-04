package com.xym.hadoop.test;

public class ThreadDemo extends Thread{
  
class Call{
  String a ;
  public Call(String a) {
    // TODO Auto-generated constructor stub
    this.a = a;
  }
}
  public void go () throws InterruptedException{
    Call c = new Call("1");
    c.wait();
  }
}
