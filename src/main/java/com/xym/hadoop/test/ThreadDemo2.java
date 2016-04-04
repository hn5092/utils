package com.xym.hadoop.test;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

// 000999 的时候
public class ThreadDemo2 extends Thread {
	private String key;
	private String value;
	private TestDo testDo;

	public ThreadDemo2(String key, String key2, String value) {
		this.testDo = TestDo.getInstance();
		/**
		 * 虽然都是1 但是不是同一个对象
		 */
		this.key = key + key2;
		/** a = "1"+""    b + "1" + "" 是同一个对象*/
		this.value = value;
	}

	public static void main(String[] args) {
		Thread a = new ThreadDemo2("1", "", "1");
		Thread b = new ThreadDemo2("1", "", "2");
		Thread c = new ThreadDemo2("3", "", "3");
		Thread d = new ThreadDemo2("4", "", "4");
		a.start();
		b.start();
		c.start();
		d.start();
	}

	@Override
	public void run() {
		testDo.doSome(key, value);
	}
}

class TestDo {
	private TestDo() {
	}

	private static TestDo _instance = new TestDo();

	public static TestDo getInstance() {
		return _instance;
	}
	private ArrayList keys = new ArrayList();
//	private CopyOnWriteArrayList<Object> keys = new CopyOnWriteArrayList<Object>();
	public void doSome(Object key, String value) {
		Object o = key ;
		/**会出现线程安全问题.在一个线程改的时候另外一个线程增加*/
		if(keys.contains(key)){
			
			for(Object oo : keys){
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				if(oo.equals(o)){
					o = oo;
				}
			}
		} else {
			keys.add(o);
		}
		
		
		
		
		synchronized (o) {
			try {
				Thread.sleep(1000);
				System.out.println(key + ":" + value + ":"
						+ (System.currentTimeMillis() / 1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}