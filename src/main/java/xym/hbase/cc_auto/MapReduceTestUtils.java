package xym.hbase.cc_auto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;


public class MapReduceTestUtils {
	public static List<Text> readDate(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
		List<Text> t = new ArrayList<Text>();
		String readLine = null;
		Text t1 = new Text();
		while ((readLine = br.readLine()) != null){
			t1.set(readLine);
			t.add(t1);
		}
		return t;
	}
}
