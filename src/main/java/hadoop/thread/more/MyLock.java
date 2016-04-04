package hadoop.thread.more;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyLock implements Lock {
	private static class Sync extends AbstractQueuedSynchronizer {
		/**
		 * 判断是否是占用状态
		 */
		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		/**
		 * 获取状态如果是0 的话就更改状态为1
		 */
		@Override
		protected boolean tryAcquire(int arg) {
			// TODO Auto-generated method stub
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		@Override
		protected boolean tryRelease(int arg) {
			if (getState() == 0)
				throw new IllegalMonitorStateException();
			setExclusiveOwnerThread(null);
			setState(0);

			return true;
		}
		 Condition newCondition(){return new ConditionObject();}
	}
	//将操作代理到sync上即可
	private final Sync sync = new Sync();
	
	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		sync.acquireInterruptibly(1);

	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		sync.release(1);

	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return sync.newCondition();
	}

}
