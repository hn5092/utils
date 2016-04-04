package test.singal;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.OBJ_ADAPTER;

public enum FooEnumSingleton {
	INSTANCE;
	public static FooEnumSingleton getInstance() {
		return INSTANCE;
		
	}
	
	public void bar() {
		System.out.println("go");
		// Integer is a "superclass" of Integer (in this context)
		List<? super Integer> foo3 = new ArrayList<Integer>();
		// Number is a superclass of Integer
		List<? super Integer> foo32 = new ArrayList<Number>();
		// Object is a superclass of Integer
		List<? super Integer> foo33 = new ArrayList<Object>();
		foo33.add((Integer) new Object());
		// Number "extends" Number (in this context)
		List<? extends Number> foo34 = new ArrayList<Number>();
		// Integer extends Number
		List<? extends Integer> foo35 = new ArrayList<Integer>();
 		// Double extends Number
		List<? extends Number> foo36 = new ArrayList<Double>();

	}
}