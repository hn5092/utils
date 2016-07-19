package hadoop.nio.fileMap;

import java.nio.ByteBuffer;

public class Test08 {

	public static void main(String[] args) {
		test();
	}
	
	public static void test() {
		ByteBuffer b = ByteBuffer.allocate(15);
		for(int i=0;i<10;i++) {
			b.put((byte)i);
		}
		ByteBuffer readOnly = b.asReadOnlyBuffer();
		readOnly.flip();
		while(readOnly.hasRemaining()) {
			System.out.print(readOnly.get()+" ");
		} 
		b.put(2,(byte)20);
		readOnly.put(2,(byte)20);
		System.out.println();
		readOnly.flip();
		while(readOnly.hasRemaining()) {
			System.out.print(readOnly.get()+" ");
		} 
		
		
	}

}
