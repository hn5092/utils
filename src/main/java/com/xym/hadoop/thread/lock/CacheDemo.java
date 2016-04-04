package com.xym.hadoop.thread.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheDemo {
	private Map<String, Object> cache = new HashMap<String, Object>();
	{
		cache.put("s", 1);
		cache.put("s1", 22);
	}

	public static void main(String[] args) {
	}

	ReadWriteLock rwl = new ReentrantReadWriteLock();
	
	public Object getData(String key) {
		rwl.readLock().lock();
		try {
			Object value = cache.get(key);
			if (value == null) {
				rwl.readLock().unlock();
				rwl.writeLock().lock();
				try {
					//防止多个线程同时到达的时候.  防止后面的程序继续写
					if(value == null ){
						value = "aaaa";
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}finally{ 
					rwl.writeLock().unlock();
				}
				rwl.readLock().lock();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			rwl.readLock().unlock();
		}

		return key;

	}
}
