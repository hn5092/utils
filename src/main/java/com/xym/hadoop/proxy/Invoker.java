package com.xym.hadoop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
/**
 * 动态代理的方法类
 * 所有动态代理都需要实现这个接口
 * @author imad
 *
 */
public class Invoker<T_T> implements InvocationHandler{
  //声明一个需要被代理的类  
  T_T t_t;
  //重载构造方法
  public Invoker(T_T t_t) {
    this.t_t = t_t;
  }
  /**
   * invoke方法 是代理的类 proxy是一个代理对象,
   * method是代理对象执行的那个方法. 比如代理对象执行master.sayhi() 方法 此时的method就是sayhi()
   * args是代理方法执行中 需要的参数   比如sayhi(参数)   参数就是args  
   * 
   */
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("come");
    System.out.println(proxy.getClass());
    method.invoke(t_t,args);
    return null;
  }
  public T_T sayhi (){
    return null;
  }

}   
