package hadoop.thread.more;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class Test19 {
	public static void main(String[] args) {
		SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
		new Customer(queue).start();
		new Product(queue).start();
	}
	static class Product extends Thread{
		SynchronousQueue<Integer> queue;
		public Product(SynchronousQueue<Integer> queue){
			this.queue = queue;
		}
		@Override
		public void run(){
			while(true){
				int rand = new Random().nextInt(1000);
				System.out.println("生产了一个产品："+rand);
				System.out.println("等待三秒后运送出去...");
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				queue.offer(rand);
			}
		}
	}
	static class Customer extends Thread{
		SynchronousQueue<Integer> queue;
		public Customer(SynchronousQueue<Integer> queue){
			this.queue = queue;
		}
		@Override
		public void run(){
			while(true){
				try {
					System.out.println("消费了一个产品:"+queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("------------------------------------------");
			}
		}
	}
	/**
	 * 运行结果：
	 *  生产了一个产品：464
		等待三秒后运送出去...
		消费了一个产品:773
		------------------------------------------
		生产了一个产品：547
		等待三秒后运送出去...
		消费了一个产品:464
		------------------------------------------
		生产了一个产品：87
		等待三秒后运送出去...
		消费了一个产品:547
		------------------------------------------
	 */
}
