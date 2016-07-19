package hadoop.nio.fileMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test10 {
	private static final String TPATH = "D:/temp.txt";
	static int courselen = 0;
	static int authlen = 0;

	//聚集写
	public static void testGatherDate() throws Exception {
		ByteBuffer courBuffer = ByteBuffer.wrap("java性能优化".getBytes("utf-8"));
		ByteBuffer autBuffer = ByteBuffer.wrap("北风网special".getBytes("utf-8"));
		courselen = courBuffer.limit();
		authlen = autBuffer.limit();
		ByteBuffer[] bufs = new ByteBuffer[]{courBuffer,autBuffer};
		File file = new File(TPATH);
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		FileChannel fc = fos.getChannel();
		fc.write(bufs);
		fos.close();
	}
	//映射读
	public static void testRead() throws Exception {
		ByteBuffer b1 = ByteBuffer.allocate(courselen);
		ByteBuffer b2 = ByteBuffer.allocate(authlen);
		ByteBuffer[] bufs = new ByteBuffer[]{b1,b2};
		File file = new File(TPATH);
		FileInputStream fis = new FileInputStream(file);
		FileChannel fc = fis.getChannel();
		fc.read(bufs);
		System.out.println(new String(bufs[0].array(),"utf-8"));
		System.out.println(new String(bufs[1].array(),"utf-8"));
		
	}
	
	public static void main(String[] args) throws Exception {
		testGatherDate();
		testRead();
	}

}
