package hadoop.thread.more;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

public class MultiThread {
	public static void main(String[] args) throws InterruptedException {
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] dumpAllThreads = threadMXBean.dumpAllThreads(false, false);
		for(ThreadInfo t:dumpAllThreads){
			System.out.println("["+t.getThreadId()+"]"+t.getThreadName());
		}
		TimeUnit.SECONDS.sleep(10);
	}
}
