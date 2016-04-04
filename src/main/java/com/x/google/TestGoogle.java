package com.x.google;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

import org.junit.Test;

import sun.security.provider.certpath.Vertex;

import com.google.common.base.Objects;
import com.google.common.collect.BoundType;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.Table;
import com.google.common.collect.TreeMultimap;
import com.google.common.collect.TreeMultiset;

public class TestGoogle {
	@Test
	public void testMinMaxPriorityQueue() {
		// 创建一个限制大小的数列 如果限制大小 那么这个数列每次加入之后会比较,如果加入数字比这个小,就自动给他
		// 把队列里面最大的数字删除,把这个小的插入
		MinMaxPriorityQueue<Integer> create = MinMaxPriorityQueue
				.maximumSize(5).create();
		MinMaxPriorityQueue<Integer> create3 = MinMaxPriorityQueue
				.expectedSize(5).create();
		// MinMaxPriorityQueue<Comparable> create = maximumSize.create();
		init(create3);
		// 使用一个数列
		List<Integer> i = new ArrayList<Integer>(10);
		i.add(1);
		i.add(3);
		i.add(2);
		i.add(5);
		MinMaxPriorityQueue<Integer> create2 = MinMaxPriorityQueue.create(i);
		System.out.println("-----------------------------");
		System.out.println(create2.peekFirst());
		
		System.out.println(create2);
		Iterable<Integer> concat = Iterables.concat(create2);
		Integer first = Iterables.getFirst(concat, 10);
		Integer last = Iterables.getLast(concat);
		System.out.println(" this is the first value : " + first);
		System.out.println(" this is the last value : " + last);
	}

	@Test
	public void testMultiset() {
		Multiset<Integer> multiset = HashMultiset.create();
		init(multiset);
		System.out.println(multiset.count(10));
		System.out.println("-------------------------");
		Set<Entry<Integer>> entrySet = multiset.entrySet();
		for (Entry<Integer> i : entrySet) {
			System.out.println(i.getCount());
		}
		System.out.println("-------------------------");

	}

	@Test
	public void testSortedMultiset() {
		TreeMultiset<Integer> create = TreeMultiset.create();
		init(create);
		// 获取一个范围内的序列
		SortedMultiset<Integer> subMultiset = create.subMultiset(1,
				BoundType.CLOSED, 10, BoundType.OPEN);
		System.out.println(subMultiset);
	}
	/**
	 * 统计每个值得个数 遇到相同的是叠加的
	 * {1=[10, 12, 13, 14], 2=[120], 3=[140], 5=[150], 6=[110], 7=[106]}
	 */
	@Test
	public void testMultimap() {
		TreeMultimap<Integer, String> create = TreeMultimap.create();
		create.put(1, "10");
		create.put(1, "13");
		create.put(1, "12");
		create.put(1, "14");
		create.put(2, "120");
		create.put(3, "140");
		create.put(5, "150");
		create.put(6, "110");
		create.put(7, "106");
		// {1=[10, 12, 13, 14], 2=[120], 3=[140], 5=[150], 6=[110], 7=[106]}
		System.out.println(create);
		Collection<String> values = create.values();
		// 循环遍历value key同理
		for (String s : values) {
			System.out.println(s);
		}
	}
	/**
	 * table 行 列 值
	 */
	@Test
	public void testTable(){
		Table<Integer, Integer, Integer> weightedGraph = HashBasedTable.create();
		weightedGraph.put(1, 2, 4);
		weightedGraph.put(1, 3, 20);
		weightedGraph.put(2, 3, 5);
		Map<Integer, Integer> row = weightedGraph.row(1); // returns a Map mapping v2 to 4, v3 to 20
		Map<Integer, Integer> column = weightedGraph.column(3); // returns a Map mapping v1 to 20, v2 to 5
		System.out.println(weightedGraph);
	}
	@Test
	public void testHash(){
		Objects.hashCode("1");
	}
	private static void init(Collection<Integer> create) {
		create.add(10);
		create.add(12);
		create.add(16);
		create.add(14);
		create.add(15);
		create.add(18);
		create.add(12);
		create.add(104);
		create.add(11);
		create.add(10);
		create.add(10);
		create.add(9);
		create.add(9);
		create.add(1);
		System.out.println(create);
		System.out.println("size:" + create.size());
		System.out.println(create);
	}
}
