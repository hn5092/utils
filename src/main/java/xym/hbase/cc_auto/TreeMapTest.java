package xym.hbase.cc_auto;

import java.util.TreeMap;

public class TreeMapTest {
	public static void main(String[] args) {
		TreeMap<Integer, String> t = new TreeMap<Integer, String>();
		
		t.put(1, "2");
		t.put(2,"3");
		t.put(0,"3");
		System.out.println(t.firstKey());
		TreeMap<String, String> t2 = new TreeMap<String, String>();
		t2.put("11", "4");
		t2.put("01", "4");
		t2.put("1", "4");
		System.out.println(t2.firstKey());
	}
}
