package hadoop.yarn.rpc.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.yarn.proto.ApplicationClientProtocol;

public class TestApplicationPb {
	public static void main(String[] args) {
		InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 8888);
		try {
			ApplicationClientProtocol p =(ApplicationClientProtocol)RPC.getProxy(ApplicationClientProtocol.class,1,  addr, new Configuration());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
