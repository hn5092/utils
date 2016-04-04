package java7.dynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;

import org.junit.Test;

public class TestInvoke {
	@Test
	public void testCreate() {
		// 无参只有一个返回值 int
		MethodType m1 = MethodType.methodType(int.class);
		// 返回 参数是String 参数是String
		MethodType mt = MethodType.methodType(String.class, String.class);
		// void (args)
		MethodType m3 = MethodType.methodType(void.class, int.class, int.class, char[].class, int.class);
		// boolean (mt) 使用其他的methodtype来进行 传递
		MethodType m4 = MethodType.methodType(boolean.class, mt);
		// 3个obj对象
		MethodType.genericMethodType(3);
		// 是否在最后添加obj[] 可变参数
		MethodType.genericMethodType(1, true);
		// ()参数,后面是返回值
		MethodType.fromMethodDescriptorString(
				"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", null);

	}

	@Test
	public void changeMethodType() {
		MethodType mt = MethodType.methodType(String.class, int.class, int.class);
		// String(int int float)
		mt = mt.appendParameterTypes(float.class);
		// int double long.int.int String
		mt = mt.insertParameterTypes(1, double.class, long.class);
		// int double int float int String 去掉了第二个
		mt = mt.dropParameterTypes(2, 3);
		mt.changeParameterType(2, String.class);
		mt.changeReturnType(void.class);
		mt.wrap(); // 装包
		mt.unwrap();// 拆包
		mt.generic();// 编程obj
		mt.erase();// 还远

	}

	@Test
	public void testGet() {
		// 查找特殊的 类 比如 私有类
		// MethodHandles.lookup().findSpecial(refc, name, type,
		// methodHandleLookup.class)

		// 通过反射api可以获得 句柄 method.unflect
	}

	@Test
	public void testArray() throws Throwable {
		int[] array = new int[] { 1, 2, 3, 4, 5, 6 };
		MethodHandle ints = MethodHandles.arrayElementSetter(int[].class);
		ints.invoke(array, 3, 6);
		MethodHandle get = MethodHandles.arrayElementGetter(int[].class);
		System.out.println(get.invoke(array, 3));
	}

	/**
	 * 插入一个参数
	 */
	@Test
	public void testInsert() throws Throwable {
		MethodHandle findVirtual = MethodHandles.lookup().findVirtual(String.class, "concat", MethodType.methodType(String.class, String.class));
		String v = (String) findVirtual.invoke("hello", "world");
		System.out.println(v);
		// 插入参数 第二个参数被替换成为world
		MethodHandle mh = MethodHandles.insertArguments(findVirtual, 1, "--");
		System.out.println(mh.invoke("hello"));
	}

	/**
	 * 过滤参数 可以把某些参数替换成为对应的methodtype
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testFilter() throws Throwable {
		MethodHandle mt = MethodHandles.lookup().findStatic(Math.class, "max", MethodType.methodType(int.class, int.class, int.class));
		MethodHandle mt2 = MethodHandles.lookup().findVirtual(String.class, "length", MethodType.methodType(int.class));
		MethodHandle mt3 = MethodHandles.filterArguments(mt, 0, mt2, mt2);
		// 使用invoke的话可以直接调用方法 如果不使用这个 需要绑定对象
		System.out.println(mt3.invoke("hello", "haha"));
	}

	public static int targetMethod(int a, int b, int c) {
		return a;
	}

	/**
	 * 把后边的得到的参数加入到前面
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testFold() throws Throwable {
		MethodHandle mt = MethodHandles.lookup().findStatic(Math.class, "max", MethodType.methodType(int.class, int.class, int.class));
		MethodHandle mt2 = MethodHandles.lookup().findStatic(TestInvoke.class, "targetMethod",
				MethodType.methodType(int.class, int.class, int.class, int.class));
		MethodHandle mt3 = MethodHandles.foldArguments(mt2, mt);
		System.out.println(mt3.invoke(3, 4)); // 4
	}

	public void say() {
		System.out.println("go");
	}

	/**
	 * 直接通过接口得到对象
	 * 
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	@Test
	public void testInterface() throws NoSuchMethodException, IllegalAccessException {
		MethodHandle mt = MethodHandles.lookup().findVirtual(TestInvoke.class, "say", MethodType.methodType(void.class));
		mt = mt.bindTo(this);
		Runnable r = MethodHandleProxies.asInterfaceInstance(Runnable.class, mt);
		new Thread(r).start();

	}

	@Test
	public void testSwithPoin() throws Throwable {
		MethodHandle mt = MethodHandles.lookup().findStatic(Math.class, "max", MethodType.methodType(int.class, int.class, int.class));

		MethodHandle mt2 = MethodHandles.lookup().findStatic(Math.class, "min", MethodType.methodType(int.class, int.class, int.class));
		System.out.println("ss");
		SwitchPoint sp = new SwitchPoint();
		MethodHandle mt3 = sp.guardWithTest(mt, mt2);
		Object invoke = mt3.invoke(3,4);
		System.out.println(invoke);
		//设置为无效之后   使用fallback
		SwitchPoint.invalidateAll(new SwitchPoint[]{sp});
		System.out.println(mt3.invoke(3,4));

	}
}
