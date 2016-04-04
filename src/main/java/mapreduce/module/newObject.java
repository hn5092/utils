package mapreduce.module;
class Human{
	String name;
	int age;
	  void eat()
	 {    
	       System.out.println("我在吃饭");
	       }
}

public class newObject {

	public static void main(String[] args) {
		
       Human wangmimg= new Human();
       int age=25;
       System.out.println("王明的年龄:"+age);  
       wangmimg.eat();
	}

}