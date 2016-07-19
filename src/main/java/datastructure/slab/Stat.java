package datastructure.slab;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Stat {
	private static ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
	
	static {
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					java.util.Iterator<String> keys = map.keySet().iterator();
					
					while(keys.hasNext()) {
						String key = keys.next();
						String val = null;
						if(map.get(key) instanceof AtomicLong) {
							val = ((AtomicLong) map.get(key)).longValue() + "";
						}
						else {
							val = (String) map.get(key);
						}
						System.out.println(key + "="+ val);
					}
					System.out.println("---------------------------------");
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public static void inc(String key) {
		AtomicLong count = (AtomicLong) map.get(key);
		
		if(count == null) {
			map.put(key, new AtomicLong(1));
		}
		else {
			count.incrementAndGet();
		}
	}
	
	public static void set(String key, String val) {
		map.remove(key);
		map.put(key, val);
	}
}
