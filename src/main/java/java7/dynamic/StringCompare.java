package java7.dynamic;

import java.text.Collator;

import org.junit.Test;

public class StringCompare {
	@Test
	public void testString() {
		Collator c = Collator.getInstance();
		c.setStrength(Collator.PRIMARY);
		int compare = c.compare("AAA", "aaa");
		System.out.println(compare);
		c.setStrength(Collator.IDENTICAL);
		compare = c.compare("AAA", "aaa");
		System.out.println(compare);
		System.gc();
	}
}
