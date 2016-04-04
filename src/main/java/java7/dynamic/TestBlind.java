package java7.dynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

import org.junit.Test;

public class TestBlind {
	@Test
	public void testBlind() throws Throwable{
		Lookup lookup = MethodHandles.lookup();
		MethodHandle findVirtual = lookup.findVirtual(String.class, "indexOf", MethodType.methodType(int.class,String.class,int.class));
		//绑定到this i是第一个参数
		MethodHandle bindTo = findVirtual.bindTo("hi").bindTo("i");
		//第二个参数
		Object invoke = bindTo.invoke(1);
		System.out.println(invoke);
		
	}
	/**
	 * 绑定基本类型需要包装
	 * @throws Throwable
	 */
	@Test
	public void testBlintbaseVO() throws Throwable{
		MethodHandle findVirtual = MethodHandles.lookup().findVirtual(String.class, "substring", MethodType.methodType(String.class,int.class,int.class));
		//基本类型要进行包装
		MethodHandle mh = findVirtual.asType(findVirtual.type().wrap());
		 mh = mh.bindTo("hello wp").bindTo(3);
		 System.out.println(mh.invoke(5));
		 
	}
}
