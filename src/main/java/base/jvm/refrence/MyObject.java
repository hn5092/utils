package base.jvm.refrence;

public class MyObject {

	@Override
	public String toString() {
		return "对象还存在";
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("对象被回收了");
	}
  
}
