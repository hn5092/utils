package com.x.protocolbuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.x.protocolbuffer.TestPeople.People;

public class Test {
	public static void main(String[] args) {
		People.Builder peBuilder = People.newBuilder().
				//设置各种属性
				setEmail("100650920").
				setName("xym").
				setId(10).
				//添加一个关联的子类
				addPhone(People.PhoneNumber.newBuilder().setNumber("1860").setType(1));
		People people = peBuilder.build();
		int length = people.getSerializedSize();
		byte[] buf = people.toByteArray();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("D:\\example.txt");
//				people.writeTo(fos);
				people.writeDelimitedTo(fos);
				fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		//这个是不对的 会超出长度限制
		try {
			FileInputStream input = new FileInputStream("D:\\example.txt");
			People people2 = People.parseDelimitedFrom(input);
			System.out.println("person2:" + people2);
			} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Read Error!");
			}
		
	}
}
