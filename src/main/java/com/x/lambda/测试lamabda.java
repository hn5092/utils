package com.x.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.x.lambda.model.TestUser;

public class 测试lamabda {

	public static void main(String[] args) {
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

		Collections.sort(names, new Comparator<String>() {
			public int compare(String a, String b) {
				return b.compareTo(a);
			}
		});
		Collections.sort(names, (a, b) -> b.compareTo(a));
		TestUser instance = TestUser.getInstance();
		// 单个参数
		Runnable noArgu = () -> System.out.println("no arguments");
		new Thread(noArgu).start();
		// Runnable multiStatement = mul -> System.out.println("no arguments");
		Runnable noArgu2 = () -> {
			System.out.println("the first row");
			System.out.println("this is second row ");
		};
		new Thread(noArgu2).start();
		BinaryOperator<Long> add = (x,y)->x-y; 
		long a = 100;
		Long apply = add.apply(1l, 2l);
		System.out.println(apply);
		
		//可以直接写好参数类型
		BinaryOperator<Long> add2 = (Long x,Long y)->x-y;
		String name = "my";
		name = "ok";
		//默认为final  如果继续使用是不行的
//		Runnable r = ()->System.out.println("1"+name);
		List<String> a1 = new ArrayList<String>();
		a1.add("1");
		a1.add("2");
		a1.add("3");
		long count = a1.stream().filter(str -> str.equals("1")).count();
		System.out.println("一共是1的元素:" + count);
		Set<String> collect = Stream.of("1","2").collect(Collectors.toSet());
		
		
		
	}

}
