package xym.hbase.cc_auto;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class BeanInfo implements WritableComparable<BeanInfo> {
	private String time; // 访问时间
	private String action; // client or AP
	private String ip; // 客户端IP
	private String screen; // 屏幕大小1
	private String URL; // 本条日志url信息
	private String fromURL; // 连接来自哪里
	private boolean isValid;// 判断该数据是否有效
	private static final int TIME_INDEX = 1;
	private static final int ACTION_INDEX = 2;
	private static final int IP_INDEX = 6;
	private static final int SCREEN_INDEX = 8;
	private static final int URL_INDEX = 9;
	private static final int FROMURL_INDEX = 12;

	public BeanInfo() {
		// TODO Auto-generated constructor stub
	}

	public static BeanInfo parser(String data, int index) {
		BeanInfo beanInfo = new BeanInfo();
		// String data =
		// "2013-08-13 20:24:46 click /auto_guide/20130531/1570394.shtml - - 122.188.105.14 1.1 Mozilla/5.0+(Windows+NT+5.1)+AppleWebKit/537.31+(KHTML,+like+Gecko)+Chrome/26.0.1410.43+BIDUBrowser/2.x+Safari/537.31+1280x800 http://auto.msn.com.cn/auto_guide/20130531/1570396_7.shtml auto.msn.com.cn activity 清凉度过一夏+13万后排出风口紧凑级车+ - - ee0c11e99efca67c4f2e45c8777b008a 984dd7f9194927e7cb8451b1e020a5ff ";
		String[] list = data.split(" ");
		if(list.length < 17){
			beanInfo.isValid = false;
			return beanInfo;
		}
		if (list[index] == "-") {
			beanInfo.isValid = false;
			return beanInfo;
		}else{
			beanInfo.isValid = true;
		}
		beanInfo.time = list[TIME_INDEX];
		beanInfo.action = list[ACTION_INDEX];
		beanInfo.ip = list[IP_INDEX];
		beanInfo.screen = list[SCREEN_INDEX] != "-" ? findscreen(list[8])
				: null;
		beanInfo.URL = list[URL_INDEX];
		beanInfo.fromURL = list[FROMURL_INDEX];
		return beanInfo;
	}

	private static String findscreen(String data) {
		String screen = data
				.substring(data.lastIndexOf("+") + 1, data.length());
		return screen;
	}

	public static BeanInfo filterIP(String data) {
		return parser(data, IP_INDEX);
	}

	public static BeanInfo filterScreen(String data) {
		return parser(data, SCREEN_INDEX);

	}
	
	public void readFields(DataInput in) throws IOException {
		in.readUTF();
		in.readUTF();
		in.readUTF();
		in.readUTF();
		in.readUTF();
		in.readUTF();
		in.readBoolean();
		
	}
	public void write(DataOutput out) throws IOException {
		out.writeUTF(time);
		out.writeUTF(action);
		out.writeUTF(ip);
		out.writeUTF(screen);
		out.writeUTF(URL);
		out.writeUTF(fromURL);
		out.writeBoolean(isValid);
	}

	public int compareTo(BeanInfo o) {
		return 0;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getFromURL() {
		return fromURL;
	}

	public void setFromURL(String fromURL) {
		this.fromURL = fromURL;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	@Override
	public String toString() {
		return  time + "\t" + action + "\t" + ip
				+ "\t" + screen + "\t" + URL + "\t"
				+ fromURL + "\t" + isValid ;
	}
	
}
