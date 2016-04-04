package com.xym.hadoop.ipc;

import java.io.IOException;

import org.apache.hadoop.ipc.ProtocolSignature;

import com.xym.hadoop.rpc.TestRPC.TestProtocol;

public class IPCimpl implements IPCStatus{

  public long getProtocolVersion(String protocol, long clientVersion) throws IOException {
    System.out.println("getProtocolVersion");
    return IPCStatus.versionID;
  }

  public ProtocolSignature getProtocolSignature(String protocol, long clientVersion,
      int clientMethodsHash) throws IOException {
    // TODO Auto-generated method stub
    return new ProtocolSignature(TestProtocol.versionID, null);
  }

  public void sayhi() {
    System.out.println("hello");
  }

  public String payFor() {
    // TODO Auto-generated method stub
    return "10";
  }
  

}
