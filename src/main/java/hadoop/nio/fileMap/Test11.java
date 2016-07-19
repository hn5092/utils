package hadoop.nio.fileMap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * 测试速度
 */
public class Test11 {
	private static int num = 4000000;

	public void testStreamWrite() throws Exception {
		long start = System.currentTimeMillis();
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(new File("d:\\temp.txt"))));
		for (int i = 0; i < num; i++) {
			dos.writeInt(i);
		}
		if (dos != null) {
			dos.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("直接使用IO耗时:" + (end - start) + "ms");
	}

	public void testStreamRead() throws Exception {
		long start = System.currentTimeMillis();
		DataInputStream dis = new DataInputStream(new BufferedInputStream(
				new FileInputStream(new File("d:\\temp.txt"))));
		for (int i = 0; i < num; i++) {
			dis.readInt();
		}
		if (dis != null) {
			dis.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("直接使用IO耗时:" + (end - start) + "ms");
	}

	public void testMapperWrite() throws Exception {
		long start = System.currentTimeMillis();
		FileChannel fc = new RandomAccessFile("d:\\temp.txt", "rw")
				.getChannel();
		IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, num * 4)
				.asIntBuffer();
		for (int i = 0; i < num; i++) {
			ib.put(i);
		}
		if (fc != null) {
			fc.close();
		}

		long end = System.currentTimeMillis();
		System.out.println("使用NIO耗时:" + (end - start) + "ms");
	}

	public void testMapperRead() throws Exception {
		long start = System.currentTimeMillis();
		FileChannel fc = new FileInputStream("d:\\temp.txt").getChannel();
		IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size())
				.asIntBuffer();
		while(ib.hasRemaining()) {
			ib.get();
		}
		if (fc != null) {
			fc.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("使用NIO耗时:" + (end - start) + "ms");
		
	}

	public static void main(String[] args) throws Exception {
		new Test11().testStreamRead();
		new Test11().testMapperRead();
	}

}
