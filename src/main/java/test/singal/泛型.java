package test.singal;

import java.util.HashSet;
import java.util.Set;

public class 泛型 {
	static class Book {
	};

	static class StoryBook extends Book implements Comparable<StoryBook> {
		public int compareTo(StoryBook o) {
			return 0; // FIXME
		}
	};

	static class TechBook extends Book implements Comparable<TechBook> {

		public int compareTo(TechBook o) {
			return 0; // FIXME
		}
	};

	public static <E> Set<E> merge(Set<? extends E> s1, Set<? extends E> s2) {
		HashSet<E> newSet = new HashSet<E>(s1);
		newSet.addAll(s2);
		return newSet;
	}

	public static void main(String[] args) {
		HashSet<StoryBook> s1 = new HashSet<StoryBook>();
		HashSet<TechBook> s2 = new HashSet<TechBook>();
		 Set<? extends Book> sb = 泛型.merge(s1, s2); // 错误
		// 需通过显式的类型参数(explicit type parameter)来告诉它要使用哪种类型
		Set<Book> bs = 泛型.<Book> merge(s1, s2);// OK
		// 或者
		Set<Comparable<?>> s = 泛型.<Comparable<?>> merge(s1, s2);
		
		
	}
}