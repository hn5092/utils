package com.x.lambda;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.x.lambda.model.User2;

public class 测试lambda高级收集器 {
	List<User2> asList;
	@Before
	public void init(){
		User2 u1 = new User2("zhang");
		User2 u2 = new User2("zhang1");
		User2 u3 = new User2("zhang2");
		asList = Arrays.asList(u1,u2,u3);
	}
	/**
	 * 自定义一个接口
	 */
	@Test
	public void testLambda() {
		StringBuilder sb = new StringBuilder("[");
		asList.stream().map(User2::getName).forEach(name ->{
			if(sb.length() > 1)
				sb.append(",");
			sb.append(name);
		});
		sb.append("]");
		System.out.println(sb.toString());
	}
	@Test
	public void testLambda2(){
		StringBuilder reduce = asList.stream().map(User2::getName).reduce(new StringBuilder(), (builder,name)->{
			if(builder.length()>1) 
				builder.append(",");
			builder.append(name);
			return builder;
		}, (left,right)->left.append(right));
		System.out.println(reduce.toString());
		
	}
	@Test
	public void testMyLambda(){
		StringCombiner reduce = asList.stream().map(User2::getName).reduce(new StringCombiner(",", "[", "]"), StringCombiner::add, StringCombiner::meger);
		System.out.println(reduce.toString());
	}
	@Test
	public void testLambda3(){
		String collect = asList.stream().map(User2::getName).collect(new StringCollect(",", "[", "]"));
		System.out.println(collect);
	}
	
	
	
	
	
}
