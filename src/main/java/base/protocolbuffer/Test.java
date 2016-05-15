package base.protocolbuffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Test {
	public static void main(String[] args) {
		TestPeople.People.Builder peBuilder = TestPeople.People.newBuilder().
				//设置各种属性
				setEmail("100650920").
				setName("xym").
				setId(10).
				//添加一个关联的子类
				addPhone(TestPeople.People.PhoneNumber.newBuilder().setNumber("1860").setType(1));
		TestPeople.People people = peBuilder.build();
		int length = people.getSerializedSize();
		byte[] buf = people.toByteArray();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("D:\\example.txt");
//				people.writeTo(fos);
				people.writeDelimitedTo(fos);
				fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Read Error!");
		}
//		try {
////			File f = new File("D:\\example.txt");
////			byte[] b = new byte[(int) f.length()];
////			FileInputStream fis = new FileInputStream(f);
////			fis.read(b);
//			People people2 = People.parseFrom(buf);
//			System.out.println("person2:" + people2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
		//这个是不对的 会超出长度限制
		try {
			FileInputStream input = new FileInputStream("D:\\example.txt");
			TestPeople.People people2 = TestPeople.People.parseDelimitedFrom(input);
			System.out.println("person2:" + people2);
			} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Read Error!");
			}
		
	}
}
