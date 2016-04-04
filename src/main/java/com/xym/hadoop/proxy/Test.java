package com.xym.hadoop.proxy;

import java.lang.reflect.Proxy;

public class Test {
  public static void main(String[] args) {
    //A是被代理对象 INVOKER是代理他的
    Invoker<A> invoker = new Invoker<A>(new A());
    Master master = (Master)Proxy.newProxyInstance(Master.class.getClassLoader(), new Class[] {Master.class},invoker);
    //代理对象执行的方法 . 相当于invoke方法里面的method = master.show()
    master.show();
    master.sayhi();
   int[] a =new int[] {1,2,3};
   
  }
}
