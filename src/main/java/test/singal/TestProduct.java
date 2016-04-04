package test.singal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class TestProduct {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException {
		Field f = TheProtectSingalModel.class.getDeclaredField("INSTANCE_CREATED");
		f.setAccessible(true);
		//设置为false 所以限制无用
		f.set(null, false);
		System.out.println();
		Constructor[] constructors = TheProtectSingalModel.class.getDeclaredConstructors();
		Constructor constructor = constructors[0];
		System.out.println(constructor);
		constructor.setAccessible(true);
		TheProtectSingalModel spuriousFoo = (TheProtectSingalModel) constructor.newInstance();
		spuriousFoo.bar();
	}
}
