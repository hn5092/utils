package base.jvm.classload;

import org.junit.Test;

/**
 * 1.当一个子类调用父类的静态方法或者参数 子类不会初始化
 * 2.new一个数组不会初始化
 * 3.常量不会初始化 常量编译时候已经存储到notinitialization类的常量池中
 * 4.接口初始化 不要求付接口全部初始化 只有真正用到父接口的时候才去初始化
 * @author xym
 *
 */
public class PassivityType {
  public static class parent {
    static {
      System.out.println("init parent");
    }
    static int value = 3;
    static final String TEST = "HAHA";
  }
  public static class child extends parent{
    static {
      System.out.println("init child");
    }
  }

  public static void main(String[] args) {
    
    //init parent
    //3
    System.out.println(child.value);
    
  }
  @Test
  public void testTwo(){
    parent[] ps = new parent[10];
  }
  @Test
  public void testThree(){
    System.out.println(parent.TEST);
  }
}
