package hadoop.thread.more;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.glassfish.grizzly.impl.UnsafeFutureImpl;
import org.junit.Test;

import sun.misc.Unsafe;

public class 手动阻塞线程 {
	

	
	static class A extends Thread{
		@Override
		public void run() {
			System.out.println("开始阻塞");
			LockSupport.park();
			System.out.println("停止阻塞");
		}
	}
	static class B extends Thread{
		@Override
		public void run() {
			
		}
	}
	@Test
	public void testPark() throws InterruptedException{
		A a = new A();
		a.start();
		TimeUnit.SECONDS.sleep(10);
		LockSupport.unpark(a);
		TimeUnit.SECONDS.sleep(10);
	}
}
