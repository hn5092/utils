package com.xym.hadoop.rmi;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ss {
	
	
	static class a {
		public a() {
			// TODO Auto-generated constructor stub
		}
	}
	static class b extends a {}
	static class c extends b {}
	public static void main(String[] args) {
		List<a> list = new ArrayList<a>();
		upperBound(list, new b());
	}
	public static void upperBound(List<? super b> list, b b)  
	{  
//	    a now = list.get(0);  
//	    System.out.println("now==>" + now);
	    list.add(b);
	    b c = (b)list.get(0);
	    //list.add(date); //这句话无法编译  
	    list.add(null);//这句可以编译，因为null没有类型信息  
	}
}
