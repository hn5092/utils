package com.x.lambda;

import org.junit.Test;

import com.x.lambda.model.TestUser;

public class TestMyLambda {
	@Test
	public void testLambda() {
		Converter<String, Integer> converter = a -> Integer.valueOf(a);
		Integer converted = converter.convert("123");
		System.out.println(converted); // 123
		Converter<String, Integer> converter2 = Integer::valueOf;
		PersonFactory<TestUser> personFactory = TestUser::getInstance;
		TestUser create = personFactory.create();
		testIF(() -> "测试这个lambda");

	}

	public static void testIF(Converter2<String> converter) {
		System.out.println(converter.convert());

	}
}
