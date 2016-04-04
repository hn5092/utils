package hadoop.thread.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class 调度线程池 {
	/**
	 * 10秒后执行,3秒后启动任务,: 
	 * delay1
	 * go
	 */
	@Test
	public void testSch() {
		ScheduledExecutorService scPool = Executors.newScheduledThreadPool(1);
		ScheduledFuture<?> schedule = scPool.schedule(new go(), 3,
				TimeUnit.SECONDS);
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
			//获取开始任务所需时间
			long delay = schedule.getDelay(TimeUnit.SECONDS);
			System.out.println("delay" + delay);
			TimeUnit.MILLISECONDS.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static class go implements Runnable {
		public void run() {
			System.out.println("go");
		}
	}
	/**
	 * 按照固定的时间 每一秒钟执行一次
	 * 内置是一个daelayqueue 
	 * @throws InterruptedException
	 */
	@Test
	public void testSchBy() throws InterruptedException{
		ScheduledExecutorService executor =     Executors.newScheduledThreadPool(1);
		Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

		int initialDelay = 0;
		int period = 1;
		executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
		TimeUnit.SECONDS.sleep(100);
	}
	/**
	 *  在上一个任务完成后间隔 1秒开始执行下一个任务
	 * @throws InterruptedException
	 */
	
	@Test
	public void testG() throws InterruptedException{
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		Runnable task = () -> {
		    try {
		        TimeUnit.SECONDS.sleep(2);
		        System.out.println("Scheduling: " + System.nanoTime());
		    }
		    catch (InterruptedException e) {
		        System.err.println("task interrupted");
		    }
		};

		executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
		TimeUnit.SECONDS.sleep(10);
	}
}
