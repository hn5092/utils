package test.singal;

public class TheProtectSingalModel {
	private static boolean INSTANCE_CREATED;
	public final static TheProtectSingalModel PROTECT_SINGAL_MODEL = new TheProtectSingalModel();
	private TheProtectSingalModel() {
		System.out.println(INSTANCE_CREATED);
		if (INSTANCE_CREATED) {
			throw new IllegalStateException(
					"You must only create one instance of this class");
		}
		INSTANCE_CREATED = true;
	}
	public void bar() {
		System.out.println("user: " + INSTANCE_CREATED);
	}
}
