package datastructure.slab;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
/**
 * 
 * @author maoyidao
 *
 * @param <K>
 * @param <V>
 */
public class LocalMemCache<K, V> {
	ConcurrentSkipListMap<Float, LocalMCSlab> slabs;
	long initSize;
	float scale = 1.27f;
	
	public LocalMemCache(long initSize, Float initChunkSize) {
		this.initSize = initSize;
		slabs = new ConcurrentSkipListMap<Float, LocalMCSlab>();
		slabs.put(initChunkSize, new LocalMCSlab(initChunkSize.intValue()));
	}

	public float getCurrentTotalCacheSize() {
		float size = 0f;
		
		for(java.util.Iterator<Float> iterator = slabs.keySet().iterator(); iterator.hasNext();) {
			Float slabsize = iterator.next();
			size += slabsize * slabs.get(slabsize).size();
			Stat.set("Chunk[" + slabsize + "] count=", slabs.get(slabsize).size() + "");
		}

		return size;
	}
	
	public boolean put(K key, byte[] value) {
		Map.Entry<Float, LocalMCSlab> entry = null;
		Float theSize = Float.valueOf(value.length);
		Stat.set("CacheSize=", ((getCurrentTotalCacheSize() / 1024f)) + "KB");
	
		// 以cache size为key，以chunks map为value，如果比这个cache size大得slab不存在，则创建一个
		// 否则，在大约cache size的slab中找一个最小的slab
		if((entry = slabs.tailMap(theSize).firstEntry()) == null) {
			Float floorKey = slabs.floorKey(theSize);
			float needSize = floorKey == null ? theSize : floorKey * scale;
				
			while(needSize < theSize) {
				needSize = needSize * scale;
			}
			
			LocalMCSlab<K, byte[]> slab = new LocalMCSlab<K, byte[]>((int) needSize);
			slab.put(key, value, false);
			slabs.put(needSize, slab);
			return true;
		}
		else {
			// 当当前全部cache size + 这个缓存的size > 分配给整个cache的initSize时，则需使用LRU策略
			boolean isLRU = getCurrentTotalCacheSize() + theSize > initSize;
			entry.getValue().put(key, value, isLRU);
			return true;
		}
	}
}
