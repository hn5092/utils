package com.x.lambda.model;


public class TestUser {
	private String name;
	private int age;
	private static final TestUser TEST_USER = new TestUser("1",2);
	public static TestUser getInstance(){
		return TEST_USER;
	}
	private TestUser (){
		
	}
	
	private TestUser(String name,int age){
		this.name = name;
		this.age = age;
	}
}
