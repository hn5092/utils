package com.x.hadoop.mr.userInfo;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.WritableComparable;

public class UserInfo implements WritableComparable<UserInfo>{
	private  String USERID;
	private  String TIME;
	private  Long date;
	private static UserInfo userInfo= new UserInfo();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Date d = null;
	private UserInfo() {
	}
	public static UserInfo getInstance(String data) throws ParseException{
		String[] datas = data.split("\t");
		userInfo.setUSERID(datas[0]);
		userInfo.setTIME(datas[1]);
		userInfo.setDate(parseDate(datas[1]));
		return userInfo;
	}
	
	private static Long parseDate(String string) throws ParseException {
		d = sdf.parse(string);
		return d.getTime();
	}
	public void write(DataOutput out) throws IOException {
		out.writeUTF(USERID);
		out.writeUTF(TIME);
		out.writeLong(date);
	}

	public void readFields(DataInput in) throws IOException {
		in.readUTF();
		in.readUTF();
		in.readLong();
	}

	public int compareTo(UserInfo o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String tIME) {
		TIME = tIME;
	}

	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long long1) {
		this.date = long1;
	}

}
