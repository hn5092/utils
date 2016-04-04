package xym.hbase.apply.dao;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import xym.hbase.apply.use.User;

public class UserDAO {
	public static final byte[] TABLE_NAME = Bytes.toBytes("auto");
	public static final byte[] INFO_FAM = Bytes.toBytes("info");
	public static final byte[] ACTION_COL = Bytes.toBytes("action");
	public static final byte[] fromURL_COL = Bytes.toBytes("fromURL");
	public static final byte[] IP_COL = Bytes.toBytes("ip");
	public static final byte[] SCREEN_COL = Bytes.toBytes("screen");
	private HConnection hConnection;
	public  UserDAO(HConnection hConnection) {
		this.hConnection = hConnection;
	}

	private static Get mkGet(String id) {
		Get g = new Get(Bytes.toBytes(id));
		g.addFamily(INFO_FAM);
		return g;

	}

	private static Put mkPut(User user) {
		Put put = new Put(Bytes.toBytes(user.id));
		put.add(INFO_FAM, ACTION_COL, Bytes.toBytes(user.action));
		put.add(INFO_FAM, fromURL_COL, Bytes.toBytes(user.fromURL));
		put.add(INFO_FAM, IP_COL, Bytes.toBytes(user.ip));
		put.add(INFO_FAM, SCREEN_COL, Bytes.toBytes(user.screen));
		return put;
	}

	private static Delete mkDel(String id) {
		Delete d = new Delete(Bytes.toBytes(id));
		return d;
	}

	public void addUser(String id, String action, String fromURL, String ip,
			String screen) throws IOException {
		HTableInterface users = hConnection.getTable(TABLE_NAME);
		Put p = mkPut(new User(action, fromURL, ip, screen));
		users.put(p);
		users.close();
	}

	public xym.hbase.apply.model.User getUser(String id) throws IOException {
		HTableInterface users = hConnection.getTable(TABLE_NAME);
		Result result = users.get(mkGet(id));
		User user = new User(result);
		users.close();
		return user;
	}

	public void deleteUser(String id) throws IOException {
		HTableInterface users = hConnection.getTable(TABLE_NAME);
		Delete d = mkDel(id);
		users.delete(d);
		users.close();
	}
}
