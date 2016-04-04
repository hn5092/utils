package com.x.hadoop.mr.userInfo;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.WritableComparable;

public class UserInfo2 implements WritableComparable<UserInfo2>{
	private  String USERID;
	private  String FristTime;
	private  String LastTime;
	private  long Times;
	private static final UserInfo2 userInfo= new UserInfo2();
	public UserInfo2() {
		// TODO Auto-generated constructor stub
	}
	static UserInfo2 getInstance(String data) throws ParseException{
		String[] datas = data.split("\\|");
		userInfo.setUSERID(datas[0]);
		userInfo.setFristTime(datas[1]);
		userInfo.setLastTime(datas[2]);
		userInfo.setTimes(Long.parseLong(datas[3]));
		return userInfo;
	}
	@Override
	public String toString() {
		return USERID + "|" + FristTime + "|" + LastTime + "|" + Times;
	}
	public void write(DataOutput out) throws IOException {
		out.writeUTF(USERID);
		out.writeUTF(FristTime);
		out.writeUTF(LastTime);
		out.writeLong(Times);
	}
	public void clear(){
		this.USERID = null;
		this.FristTime = null;
		this.LastTime = null;
		this.Times = 0 ;
	}

	public void readFields(DataInput in) throws IOException {
		this.USERID = in.readUTF();
		this.FristTime = in.readUTF();
		this.LastTime = in.readUTF();
		this.Times = in.readLong();
	}

	public int compareTo(UserInfo2 o) {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public String getFristTime() {
		return FristTime;
	}
	public void setFristTime(String fristTime) {
		FristTime = fristTime;
	}
	public String getLastTime() {
		return LastTime;
	}
	public void setLastTime(String lastTime) {
		LastTime = lastTime;
	}
	public long getTimes() {
		return Times;
	}
	public void setTimes(long times) {
		Times = times;
	}


}
