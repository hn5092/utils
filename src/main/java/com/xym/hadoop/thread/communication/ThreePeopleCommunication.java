package com.xym.hadoop.thread.communication;

import java.security.Principal;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class ThreePeopleCommunication {
	
	

	public static void main(String[] args) {
		final ComTest comTest = new ComTest();
		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 100; i++) {
					comTest.sayhi1();
				}
			}
		}).start();
		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 100; i++) {
					comTest.sayhi2();
				}
			}
		}).start();
		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 100; i++) {
					comTest.sayhi3();
				}
			}
		}).start();
	}
static class ComTest{
	 Lock lock = new ReentrantLock();
	 Condition c1 = lock.newCondition();
	 Condition c2 = lock.newCondition();
	 Condition c3 = lock.newCondition();
	 int sub = 1;
	public  void sayhi1() {
		lock.lock();
		try {
			while (sub != 1) {
				c1.await();
			}
			System.out.println(Thread.currentThread().getName() + "  1");
			sub = 2;
			c2.signal();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			lock.unlock();
		}

	}

	public  void sayhi2() {
		lock.lock();
		try {
			while (sub != 2) {
				c2.await();
			}
			System.out.println(Thread.currentThread().getName() + "  2");
			sub = 3;
			c3.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}

	public  void sayhi3() {
		lock.lock();
		try {
			while (sub != 3) {
				c3.await();
			}
			System.out.println(Thread.currentThread().getName() + "  3");
			sub = 1;
			c1.signal();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			lock.unlock();
		}

	}
}
	
}
