package com.xym.hadoop.ipc;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;

public class IPCService {
	public static final int IPC_PORT = 8888;
	public static final long IPC_VER = 5473L;

	static final Log LOG = LogFactory.getLog(IPCService.class);
	public static void main(String[] args) {
		// new一个默认的shconfiguration
		LOG.isDebugEnabled();
		IPCimpl ipCimpl = new IPCimpl();
		Server server = null;
		try {
			server = new RPC.Builder(new Configuration())
					.setProtocol(IPCStatus.class).setInstance(ipCimpl)
					.setBindAddress("127.0.0.1").setPort(IPC_PORT).build();
			server.start();
		} catch (HadoopIllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("successful");
		}

	}
}
