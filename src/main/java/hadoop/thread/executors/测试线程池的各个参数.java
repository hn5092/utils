package hadoop.thread.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 
 * @author imad 线程池参数详解: 1. 常驻线程,即使是在空闲时候也要保持的核心线程数 2. 最大的线程数,最大能同事运行的线程 3.
 *         线程空闲的存活时间,当线程空闲时间超过这个值的时候 就会kill掉这个线程 4. 线程池的
 *         任务队列,在这个线程池中执行的任务,如果当前需要执行的任务大于这个队列值,就new多少个线程,但是总的线程大小不能超过 线程池的最大线程数
 */
public class 测试线程池的各个参数 {
	public static void main(String[] args) throws InterruptedException {
		// ExecutorService threadPool = Executors.newFixedThreadPool(3);
		// ExecutorService threadPool = Executors.newCachedThreadPool();
		// 线程死了重新启动
		// ExecutorService threadPool = Executors.newSingleThreadExecutor();
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 20, 1L,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1)
			);
		for (int i = 0; i < 20; i++) {
			final int index = i;
			threadPool.execute(new Runnable() {
				public void run() {
					int activeCount = threadPool.getPoolSize();
					int maximumPoolSize = threadPool.getMaximumPoolSize();
//					System.out.println("this is " + activeCount
//							+ "this is max : " + maximumPoolSize);
					System.out.println(Thread.currentThread().getName()
							+ " this loop of " + index);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		Thread.currentThread().sleep(16000000);
		int activeCount = threadPool.getPoolSize();
		int maximumPoolSize = threadPool.getMaximumPoolSize();
		System.out.println("this is " + activeCount + "this is max : "
				+ maximumPoolSize);
		// threadPool.shutdown();

		//
		// Executors.newScheduledThreadPool(3).schedule(new Runnable() {
		// public void run() {
		// System.out.println("boom");
		// }
		// }, 10, TimeUnit.SECONDS);

	}
}
