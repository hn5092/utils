package hadoop.thread.more;

import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import org.junit.Test;

public class TestBlockQueue {
	/**
	 * test ArrayBlockingQueue 数组结构组成的有界阻塞队列
	 */
	@Test
	public void testArray() {
		// 开启了 线程公平
		ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<Integer>(
				100, true);
	}

	/**
	 * LinkedBlockingQueue 链表组成的有界阻塞队列
	 */
	@Test
	public void testLinked() {
		LinkedBlockingDeque<Integer> linkedBlockingDeque = new LinkedBlockingDeque<Integer>(100);
		
	}
	
	/**
	 * 
	 * priorityBlockingQueue 默认才去升序
	 */
	@Test
	public void testPriority(){
		//可以自定义实现 compare 或者直接调用对象的compare
		new PriorityBlockingQueue<Integer>(10,new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return 0;
			}
		});
	}
	/**
	 * delayQueue
	 */
	@Test
	public void testDelay(){
		
	}
	/**
	 * 需要2个线程之间传递 不存储任何数据 起到传递的作用效率高于linkedblockingQueue arrayblockingqueue
	 */
	@Test
	public void testSynchronous(){
		SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<Integer>();
			synchronousQueue.offer(2);
			System.out.println(synchronousQueue.poll());
	}
	
	
	/**
	 * LinkedTransferQueue   如果有消费者等待  则会立刻传递给消费者 如果没有   就会插入链表第一个
	 */
	@Test
	public void testTransfer(){
		LinkedTransferQueue<Integer> linkedTransferQueue = new LinkedTransferQueue<Integer>();
		linkedTransferQueue.add(1);
		System.out.println(linkedTransferQueue.peek());
	}
	/**
	 * linkedBlockingDeque
	 * @throws InterruptedException 
	 */
	@Test
	public void testDeque() throws InterruptedException{
		LinkedBlockingDeque<Integer> linkedBlockingDeque = new LinkedBlockingDeque<Integer>(10);
		linkedBlockingDeque.addFirst(1);
		linkedBlockingDeque.addLast(1);
		linkedBlockingDeque.takeFirst();
		linkedBlockingDeque.takeLast();
	}
}
