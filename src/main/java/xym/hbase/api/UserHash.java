package xym.hbase.api;

import org.junit.Test;


public class UserHash {
	@Test
	public void hashTest(){
		String a = "asldjaslkjdaljddddddddddddddddddddddddadsd";
		Integer hashCode = a.hashCode();
		System.out.println(hashCode);
		short shortValue = hashCode.shortValue();
		System.out.println(shortValue);
	}
}
