package base.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.junit.Test;

@SuppressWarnings("unused")
public class MethodTypeTest {
     
    /**
     * 创建methodtype，只和参数类型和返回类型有关
     */
    @Test
    public void createMethodType() {
        //String.length()   
        MethodType mt1 = MethodType.methodType(int.class);
        //String.concat(String str)
        MethodType mt2 = MethodType.methodType(String.class, String.class);
        //String.startWith(String prefix)
        MethodType mt3 = MethodType.methodType(boolean.class, mt2);
    }
     
    /**
     * 生成通用MethodType,即参数类型返回值都是Object
     */
    @Test
    public void generateGenericMethodTypes() {
        MethodType mt1 = MethodType.genericMethodType(3);
        //是否在参数末尾追加Object[]
        MethodType mt2 = MethodType.genericMethodType(2, true);
    }
     
    /**
     * 使用JVM规范中的方法描述符创建methodtype
     */
    @Test
    public void generateMethodTypesFromDescriptor() {
        ClassLoader cl = this.getClass().getClassLoader();
        String descriptor = "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;";
        MethodType mt1 = MethodType.fromMethodDescriptorString(descriptor, cl);
        System.out.println(mt1);
    }
     
    /**
     * 动态修改MethodType
     */
    @Test
    public void changeMethodType() {
        //(int int) String
        MethodType mt = MethodType.methodType(String.class, int.class, int.class);
        //(int int float) String
        mt = mt.appendParameterTypes(float.class);
        //(int double long int float) String
        mt = mt.insertParameterTypes(1, double.class, long.class);
        //(int double int float) String
        mt = mt.dropParameterTypes(2, 3);
        //(int double long float) String
        mt = mt.changeParameterType(2, long.class);
        //(int double long float) int
        mt = mt.changeReturnType(int.class);
    }
     
    /**
     * 一次性修改所有参数和返回类型
     */
    @Test
    public void wrapandGeneric() {
        //(int double) Integer
        MethodType mt = MethodType.methodType(Integer.class, int.class, double.class);
        //(Integer Double) Integer
        mt = mt.wrap();
        //(int double) int
        mt = mt.unwrap();
        //(Object Object) Object
        mt = mt.generic();
        //(int double)Object
        mt = mt.erase();
    }
     
    /**
     * invokeExact
     * @throws Throwable 
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     */
    @Test
    public void invokeExact() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(String.class, int.class, int.class);
        MethodHandle mh = lookup.findVirtual(String.class, "substring", mt);
        String str = (String)mh.invokeExact("Hello World", 1, 3);
        System.out.println(str);
    }
}