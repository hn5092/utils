package hadoop.test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class test {
	public static void main(String[] args) {
		byte[] a = new byte[24];
		IntBuffer i = ByteBuffer.wrap(a)
		         .order(ByteOrder.nativeOrder())
		         .asIntBuffer();
		i.put(1);
		System.out.println(i.capacity());
		System.out.println(i.position());
		i.put(1);
		System.out.println(i.position());
	}
}
