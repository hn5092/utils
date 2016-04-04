package com.x.hadoop.mr.bbs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class PostInfo implements WritableComparable<PostInfo>{
	private String id;
	private String time;
	private String count;
	private static PostInfo postInfo = new PostInfo();
	private PostInfo() {
		// TODO Auto-generated constructor stub
	}
	public static PostInfo getInstance(String value){
		String[] data = value.split("\t");
		postInfo.setId(data[2]);
		postInfo.setTime(data[1]);
		postInfo.setContent(data[0]);
		return postInfo;
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "B"+this.time + "|" + this.count;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return count;
	}
	public void setContent(String content) {
		this.count = content;
	}
	public void write(DataOutput out) throws IOException {
		out.writeUTF(id);
		out.writeUTF(time);
		out.writeUTF(count);
	}
	public void readFields(DataInput in) throws IOException {
		this.id = in.readUTF();
		this.time = in.readUTF();
		this.count = in.readUTF();
	}
	public int compareTo(PostInfo o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
