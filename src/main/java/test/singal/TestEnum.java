package test.singal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestEnum {
	public static void main(String[] args) throws Exception {
		FooEnumSingleton instance = FooEnumSingleton.getInstance();
		instance.bar();
		Constructor con = FooEnumSingleton.class.getDeclaredConstructors()[0];
		Class<? extends Constructor> class1 = con.getClass();
		FooEnumSingleton newInstance = (FooEnumSingleton)con.newInstance(new Object[0]);
		con.setAccessible(true);
		newInstance.bar();
		
		//使用此方法可以掉用私有方法的构造器
		Method[] methods = class1.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.getName());
			if (method.getName().equals("acquireConstructorAccessor")) {
				method.setAccessible(true);
				method.invoke(con, new Object[0]);
			}
		}
		Field[] fields = class1.getDeclaredFields();
		Object ca = null;
		for (Field field : fields) {
			if (field.getName().equals("constructorAccessor")) {
				field.setAccessible(true);
				ca = field.get(con);
			}
		}
//		Method method = ca.getClass().getMethod("newInstance",
//				new Class[] { Object[].class });
//		method.setAccessible(true);
//		FooEnumSingleton spuriousEnum = (FooEnumSingleton) method.invoke(ca,
//				new Object[] { new Object[] { "SPURIOUS_INSTANCE", 1 } });
//		printInfo(FooEnumSingleton.INSTANCE);
//		printInfo(spuriousEnum);
	}

	private static void printInfo(FooEnumSingleton e) {
		System.out.println(e.getClass() + ":" + e.name() + ":" + e.ordinal());
	}
}
