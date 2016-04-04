package datastructure.array.object;

public class UserModel {
	private int uuid;
	private  String  name ;
	private int age;
	public int getUuid() {
		return uuid;
	}
	
	public UserModel(int uuid) {
		super();
		this.uuid = uuid;
	}
	public UserModel(int uuid, String name, int age) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.age = age;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "UserModel [uuid=" + uuid + ", name=" + name + ", age=" + age
				+ "]";
	}
	
}
