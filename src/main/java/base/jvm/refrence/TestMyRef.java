package base.jvm.refrence;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class TestMyRef {

	ReferenceQueue<MyObject> softQueue = null;
	ReferenceQueue<MyObject> weakQueue = null;
	ReferenceQueue<MyObject> phantQueue = null;

	public class CheckRefQueue extends Thread {
		protected ReferenceQueue<MyObject> queue;
		protected String queueName;

		public CheckRefQueue(ReferenceQueue<MyObject> queue, String queueName) {
			this.queue = queue;
			this.queueName = queueName;
		}

		@Override
		public void run() {
			while (true) {
				Reference<MyObject> obj = null;
				try {
					obj = (Reference<MyObject>) queue.remove();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (obj != null) {
					System.out.println("对象:" + queueName + "是" + obj.get());
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void test() {
		MyObject obj = new MyObject();
		// softQueue = new ReferenceQueue<MyObject>();
		//weakQueue = new ReferenceQueue<MyObject>();
		phantQueue = new ReferenceQueue<MyObject>();
		// SoftReference<MyObject> softRef = new SoftReference<MyObject>(obj,
		// softQueue);
		//WeakReference<MyObject> weakRef = new WeakReference<MyObject>(obj,
				//weakQueue);
		PhantomReference<MyObject> phantRef = new PhantomReference<MyObject>(
				obj, phantQueue);
		// new CheckRefQueue(softQueue, "软引用队列").start();
		//new CheckRefQueue(weakQueue, "弱引用队列").start();
		new CheckRefQueue(phantQueue, "需引用队列").start();
		// 去除强引用
		obj = null;
		// System.out.println("GC之前"+softRef.get());
		//System.out.println("GC之前" + weakRef.get());
		System.out.println("GC之前" + phantRef.get());
		System.gc();
		// System.out.println("GC后"+softRef.get());
		//System.out.println("GC之后" + weakRef.get());
		System.out.println("GC之后" + phantRef.get());
		System.out.println("分配一个大内存");
		byte[] b = new byte[4 * 1024 * 9250];
		// System.out.println("分配一个大内存后"+softRef.get());
		//System.out.println("分配一个大内存后" + weakRef.get());
		System.out.println("分配一个大内存后" + phantRef.get());
	}

	public static void main(String[] args) {
		new TestMyRef().test();
	}

}
