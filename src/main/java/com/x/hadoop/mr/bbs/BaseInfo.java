package com.x.hadoop.mr.bbs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class BaseInfo implements WritableComparable<BaseInfo>{
	private String Id;
	private String name;
	private String gender;
	private int age;
	static BaseInfo baseInfo = new BaseInfo();
	private BaseInfo() {
		// TODO Auto-generated constructor stub
	}
	public static BaseInfo getInstance(String value){
		String[] data = value.split("\t");
		baseInfo.setId(data[0]);
		baseInfo.setName(data[1]);
		baseInfo.setGender(data[2]);
		baseInfo.setAge(Integer.parseInt(data[3]));
		return baseInfo;
		
	}
	@Override
	public String toString() {
		return "A"+this.name+"|" + this.gender + "|" + this.age;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void write(DataOutput out) throws IOException {
		out.writeUTF(this.Id);
		out.writeUTF(name);
		out.writeUTF(gender);
		out.writeInt(age);;
		
	}
	public void readFields(DataInput in) throws IOException {
		this.Id = in.readUTF();
		this.name = in.readUTF();
		this.gender = in.readUTF();
		this.age = in.readInt();
	}
	public int compareTo(BaseInfo o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
