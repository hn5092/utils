package java7;

import com.google.common.io.Files;
import org.apache.commons.io.Charsets;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by hadoop on 5/16/2016.
 */
public class TestWriterFile {


    @Test
    public void testWriter() throws IOException, InterruptedException {

        File f1 = new File("d:/test");
        f1.delete();
        f1.createNewFile();
        BufferedWriter bw = java.nio.file.Files.newBufferedWriter(Paths.get("d:/test"),
                Charset.forName("UTF-8"), StandardOpenOption.WRITE);
        int count = 1;
        while (count < 10) {
            bw.write("file1line"+count);
            count++;
            Thread.currentThread().sleep(1000);
        }
        bw.close();

    }


    @Test
    public void testRead() throws IOException {
        File f1 = new File("d:/test");
        BufferedReader gbk = java.nio.file.Files.newBufferedReader(Paths.get("d:/test"), Charset.forName("GBK"));
        String s = "";
        while (s != null) {
            s = gbk.readLine();
            System.out.println(s);
        }
        gbk.close();
    }


}
