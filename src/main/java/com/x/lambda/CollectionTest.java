package com.x.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import scala.collection.mutable.HashMap;

public class CollectionTest {
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(new Integer[] { 1, 2, 3, 4 });
		List<String> list5 = Arrays.asList("john", "paul", "george", "john",
				"paul", "john");
		List<Integer> collect = numbers.stream().collect(Collectors.toList());
		// group by可以根绝 自由分组
		Map<Integer, List<Integer>> collect2 = numbers.stream().collect(
				Collectors.groupingBy(x -> x));
		List<Integer> list = collect2.get(1);
		for (Integer i : list) {
			System.out.println(i);
		}
		System.out.println("----------------");
		List<Integer> list2 = collect2.get(2);
		for (Integer i : list2) {
			System.out.println(i);

		}
		System.out.println("----------------");

		System.out.println(numbers.size());
		// partition只能分成对或者错误的
		Map<Boolean, List<Integer>> collect3 = numbers.stream().collect(
				Collectors.partitioningBy(x -> x == 1));
		List<Integer> list3 = collect3.get(true);
		for (Integer i : list3) {
			System.out.println(i);
		}
		System.out.println("----------------");
		List<Integer> list4 = collect3.get(false);
		for (Integer i : list4) {
			System.out.println(i);
		}
		System.out.println("----------------");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		list5.stream().collect(Collectors.groupingBy(x -> x))
				.forEach((na, s) -> {
					map.put(na, s.size());
				});
		System.out.println(map.get("john"));
	}
}
