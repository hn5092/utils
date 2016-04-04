package com.xym.hadoop.ipc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class RPCClient {
  public static void main(String[] args) {
    InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 8888);
    try {
      IPCStatus ipcStatus=RPC.getProxy(IPCStatus.class, IPCStatus.versionID, addr, new Configuration());
      ipcStatus.sayhi();
      String hi = ipcStatus.payFor();
      System.out.println(hi);
      RPC.stopProxy(ipcStatus);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
