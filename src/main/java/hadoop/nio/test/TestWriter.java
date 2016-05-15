package hadoop.nio.test;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xym on 2016/5/13.
 */
public class TestWriter {
    @Test
    public void testWriter() throws IOException {
        RandomAccessFile rw = new RandomAccessFile("e:/go.text", "rw");
        FileChannel channel = rw.getChannel();
        String positionStr = String.valueOf(11);
        int bufferSize = positionStr.length();
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        buffer.clear();
        buffer.put(positionStr.getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        rw.close();
    }
}
