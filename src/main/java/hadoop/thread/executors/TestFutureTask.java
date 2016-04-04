package hadoop.thread.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TestFutureTask {
	/**
	 * futuretask 可以返回一个值 这个任务也可以欧诺个过
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void testFutureTask() throws InterruptedException,
			ExecutionException {
		Runnable task = () -> System.out.println("Scheduling: "
				+ System.nanoTime());
		Callable<Integer> c = () -> {
			TimeUnit.SECONDS.sleep((long) (Math.random() * 1000));
			System.out.println("11");
			return 1;
		};
		FutureTask<Integer> futureTask = new FutureTask<Integer>(c);
		futureTask.run();
		Integer integer = futureTask.get();
		System.out.println("this is result" + integer);
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		Future<?> submit = threadPool.submit(c);
		Object x = submit.get();

		TimeUnit.SECONDS.sleep(1000);

	}
}
