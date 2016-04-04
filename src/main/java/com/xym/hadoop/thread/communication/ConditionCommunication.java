package com.xym.hadoop.thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionCommunication {
	//多个condition   当你有多个 不同方式的同时访问的话    需要使用不同的condition 
	public static void main(String[] args) throws InterruptedException {
		final ConditionCommunication.Bussiness bussiness = new ConditionCommunication.Bussiness();
		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i <= 50; i++) {
					// synchronized (TraditionalThreadCommunication.class) {

					try {
						bussiness.sub(i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// }
				}
			}
		}).start();

		for (int i = 0; i <= 50; i++) {
			// synchronized (TraditionalThreadCommunication.class) {
			// }
			bussiness.main(i);
		}
	}

	static class Bussiness {
		Lock lock = new ReentrantLock();
		//使用condition 进行通讯. 
		Condition condition = lock.newCondition();
		private boolean bShouldSub = true;

		public void sub(int i) throws InterruptedException {
			lock.lock();
			try {
				while (!bShouldSub) { // 代码更健壮 防止伪唤醒
					condition.await();
				}
				for (int j = 0; j <= 50; j++) {
					System.out.println("sub thread" + i + "loop" + j);
				}

				bShouldSub = !bShouldSub;
				condition.signal();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				lock.unlock();
			}

		}

		public void main(int i) throws InterruptedException {
			lock.lock();
			try {
				if (bShouldSub) {
					condition.await();
				}
				for (int j = 0; j <= 100; j++) {
					System.out.println("main thread" + i + "loop" + j);
				}
				bShouldSub = !bShouldSub;
				condition.signal();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				lock.unlock();
			}

		}
	}
}