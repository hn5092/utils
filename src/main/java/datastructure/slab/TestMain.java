package datastructure.slab;
/**
 * 
 * @author maoyidao
 *
 */
public class TestMain {
	public static void main(String args[]) {
		// init local mc size = 1000 byte
		LocalMemCache cache = new LocalMemCache(1024 * 1024 * 1024, 100f);
		
		while(true) {
			for(int i = 0; i < 9999999; i++) {
				byte[] data = new byte[new java.util.Random().nextInt(1024 * 200)];
				
				for(int j = 0; j < data.length; j++) {
					data[j] = 1;
				}
				
				cache.put(i, data);
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
