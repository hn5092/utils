package datastructure.queue;

import java.util.TreeMap;

import org.apache.commons.collections.list.TreeList;
import org.junit.Test;

/**
 * 循环队列
 * 
 * @author imad
 *
 */
public class MyQueue<T> {
	/**
	 * 存放队列数据的数组
	 */
	private Object[] queue;
	// 指针
	private int front;
	// 结束为止
	private int end;
	// 个数
	private int nItems;

	MyQueue() {

	}

	public MyQueue(int length) {
		queue = new Object[length];
		front = 0;
		end = -1;
		nItems = 0;
	}

	public void insert(T data) {
		// 检查是否已经到数组尾部
		if (end == queue.length - 1) {
			end = -1;
		}
		end++;
		queue[end] = data;
		nItems++;
		if (nItems > queue.length) {
			nItems = queue.length;
		}

	}

	public T remove() {
		if (nItems == 0) {
			return null;
		}
		T temp = (T) queue[front];
		queue[front] = null;
		// 重置 当指针完毕就重置
		if (front == queue.length - 1) {
			front = 0;
		}
		front++;
		nItems--;
		return temp;
	}

	public T peekFont() {
		return (T) queue[front];
	}

	public boolean isEmpty() {
		return nItems == 0;
	}

	public boolean isFull() {
		return nItems == queue.length - 1;
	}

	public void printQueue() {
		System.out.println("----------------------------------");
		for (Object t : queue) {
			System.out.println((T) t);
		}
	}
	public static void main(String[] args) {
		TreeMap<Integer, String> tm = new TreeMap<Integer, String>();
		tm.put(1, "2");
		tm.put(1, "3");
		String string = tm.get(1);
		System.out.println(string);  
		MyQueue<String> myQueue = new MyQueue<String>(4);
		myQueue.insert("1");
		myQueue.insert("2");
		myQueue.insert("3");
		myQueue.insert("4");
		myQueue.peekFont();
		myQueue.insert("5");
		myQueue.printQueue();
		
	}
}
