package com.xym.hadoop.proxy;
/**
 * 申明被代理对象的接口.通过动态代理 接口中的所有方法都会被代理
 * @author imad
 *
 */
public interface Master {
    public void show();
    public void sayhi();
}
