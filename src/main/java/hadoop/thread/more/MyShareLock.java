package hadoop.thread.more;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.junit.Test;

public class MyShareLock implements Lock {
	/**
	 * 设置最多n个线程的共享锁
	 * 
	 * @author imad
	 *
	 */
	private static class Sync extends AbstractQueuedSynchronizer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Sync(int count) {
			if (count < 0) {
				throw new IllegalArgumentException("count must large than zero");
			}
			setState(count);
		}

		/**
		 * 1.得到当前的状态 2.当前状态减少1 3.更新或者当前获取失败就返回当前的count >0表示成功
		 */
		@Override
		protected int tryAcquireShared(int reduceCount) {
			for (;;) {
				int current = getState();
				System.out.println("this is current status in acquire:"
						+ current);
				int newCount = current - reduceCount;
				if (newCount < 0 || compareAndSetState(current, newCount)) {
					return newCount;
				}
			}
		}

		/**
		 * 进行死循环 首先获取状态 然后把之前的状态设置为新的状态 +1
		 */
		@Override
		protected boolean tryReleaseShared(int returnCount) {
			for (;;) {
				int current = getState();
				System.out.println("this is current status in release : "
						+ current);

				int newCount = current + returnCount;
				if (compareAndSetState(current, newCount)) {
					System.out.println("release : " + newCount);
					return true;
				}
			}
		}

	}

	private final Sync sync = new Sync(2);

	@Override
	public void lock() {
		sync.acquireShared(1);

	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {
		sync.releaseShared(1);
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Test
	public void testMyLock() {
		final MyShareLock lock = new MyShareLock();
		class Work extends Thread {
			@Override
			public void run() {
				while(true) {
					lock.lock();
					try {
						System.out.println(Thread.currentThread().getName()+"-------------------------------");
//						sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						lock.unlock();
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		 
		for (int i = 0; i < 10; i++) {
			Work work = new Work();
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			work.setDaemon(true);
			work.start();
		}
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(10000);
				System.out.println();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Test
	public void testW(){
		int size =2;
		System.out.println((1<<16+1<<16+1<<16+1<<16+1<<16)>>>16);
		System.out.println(((1<<16+1<<16+1<<16+1<<16+1<<16)&1 << 16) - 1);
		System.out.println(size <<= 1);
		
	}

}
