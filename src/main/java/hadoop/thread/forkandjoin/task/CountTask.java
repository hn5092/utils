package hadoop.thread.forkandjoin.task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD = 100;// 阈值
	private Long start;
	private Long end;

	public CountTask(Long start, Long end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		Long sum = 0l;
		boolean canCompute = (end - start) <= THRESHOLD;
		if (canCompute) {
			for (Long i = start; i <= end; i++) {
				sum += i;
			}
		} else {
			long middle = (start + end) / 2;
			CountTask countTask = new CountTask(start, middle);
			CountTask countTask2 = new CountTask(middle + 1, end);
			//执行子任务
			countTask.fork();
			countTask2.fork();
			Long join = countTask.join();
			Long join2 = countTask2.join();
			sum = join + join2;
		}
		return sum;
	}
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(10);
		CountTask countTask = new CountTask(1L, 444444112332144L);
		ForkJoinTask<Long> submit = forkJoinPool.submit(countTask);
		try {
			System.out.println(submit.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
