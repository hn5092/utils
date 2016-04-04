package xym.hbase.apply.use;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class User extends xym.hbase.apply.model.User {
	public static final byte[] TABLE_NAME = Bytes.toBytes("auto");
	public static final byte[] INFO_FAM = Bytes.toBytes("info");
	public static final byte[] ACTION_COL = Bytes.toBytes("action");
	public static final byte[] fromURL_COL = Bytes.toBytes("fromURL");
	public static final byte[] IP_COL = Bytes.toBytes("ip");
	public static final byte[] SCREEN_COL = Bytes.toBytes("screen");
	//进行增加时候使用的对象
	public User(String action, String fromURL, String ip, String screen) {
		this.action = action;
		this.fromURL = fromURL;
		this.ip = ip;
		this.screen = screen;
	}
	/**
	 * 为了查找的对象
	 * @param result
	 */
	public User(Result result) {
		this(result.getValue(INFO_FAM, ACTION_COL), result.getValue(INFO_FAM,
				fromURL_COL), result.getValue(INFO_FAM, IP_COL), result
				.getValue(INFO_FAM, SCREEN_COL));
	}
	
	/**
	 * 查找之后返回了byte数组的对象
	 * @param action
	 * @param fromURL
	 * @param ip
	 * @param screen
	 */
	public User(byte[] action, byte[] fromURL, byte[] ip, byte[] screen) {
		this(Bytes.toString(action), Bytes.toString(fromURL), Bytes
				.toString(ip), Bytes.toString(screen));
	}
}
