package com.x.google;

import java.util.List;

import javax.swing.text.StyledEditorKit.ItalicAction;

import org.junit.Test;

import com.google.common.base.Function;
//import com.google.common.base.MoreObjects;
//import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.x.hadoop.mr.bbs.test;

public class TestCommon {
	public String name;

	@Test
	public void testToString() {
		TestCommon testCommon = new TestCommon();
		testCommon.setName("11");
		System.out.println(testCommon.toString());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return MoreObjects.toStringHelper(this).add("name", name).toString();
//	}

	/**
	 * 测试比较器
	 */
	@Test
	public void testCompare() {
		Ordering<String> byLengthOrdering = new Ordering<String>() {
			public int compare(String left, String right) {
				return Ints.compare(left.length(), right.length());
			}
		};
		byLengthOrdering.onResultOf(new Function<String, String>() {

			public String apply(String input) {
				return null;
			}
		});
	}

	/**
	 * 测试iterables
	 */
	@Test
	public void testIterables() {
		Iterable<Integer> concatenated = Iterables.concat(Ints.asList(1, 2, 3),
				Ints.asList(4, 5, 6)); // concatenated包括元素 1, 2, 3, 4, 5, 6
		Integer lastAdded = Iterables.getLast(concatenated);
		System.out.println(lastAdded);
		int frequency = Iterables.frequency(concatenated, 1);
		System.out.println("在迭代器中出现1的次数是"+frequency);
		//只有单一的不重复的元素才争取
//		Integer theElement = Iterables
//				.getOnlyElement(concatenated);
//		System.out.println(theElement);
		// 如果set不是单元素集，就会出错了！
		
		
		List<Integer> asList = Ints.asList(1,2,3,4,5);
		
	}
}
