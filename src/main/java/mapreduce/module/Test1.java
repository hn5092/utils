package mapreduce.module;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;

public class Test1 {
	public static void main(String[] args) {
		List<Text> a = new ArrayList<Text>();
		List<Text> b = new ArrayList<Text>();
		List<Text> v = new ArrayList<Text>();
		v.add(new Text("U1"));
		v.add(new Text("B1"));
		v.add(new Text("U2"));
//		System.out.println(v.toString());
//		System.out.println(a.toString());
//		System.out.println(b.toString());
		
		
		for(Text t: v){
			if(t.toString().startsWith("U")){
				a.add(t);
			}else{
				b.add(t);
			}
		}
		System.out.println(a.toString());
		System.out.println(b.toString());
	}
}
