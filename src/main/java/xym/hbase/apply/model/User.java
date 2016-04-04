package xym.hbase.apply.model;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

import xym.hbase.apply.dao.UserDAO;

public abstract class User {
	public String id;
	public String action;
	public String fromURL;
	public String ip;
	public String screen;

	public User() {
	}
	
	public User(String id, String action, String fromURL, String ip,
			String screen) {
		this.id = id;
		this.action = action;
		this.fromURL = fromURL;
		this.ip = ip;
		this.screen = screen;
	}

	
	public static void main(String[] args) {
		Configuration con = new Configuration();
		Configuration conf = HBaseConfiguration.create(con);
		conf.set("hbase.zookeeper.quorum", "xym01:2181,xym02:2181,xym03:2181");
		try {
			HConnection createConnection = HConnectionManager
					.createConnection(conf);
			UserDAO user = new UserDAO(createConnection);
			User user2 = user.getUser("10010");
			System.out.println(user2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", action=" + action + ", fromURL=" + fromURL
				+ ", ip=" + ip + ", screen=" + screen + "]";
	}
}	
