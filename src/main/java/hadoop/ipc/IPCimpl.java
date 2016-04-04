package hadoop.ipc;

import java.io.IOException;

import hadoop.rpc.TestRPC;
import org.apache.hadoop.ipc.ProtocolSignature;

public class IPCimpl implements IPCStatus{

  public long getProtocolVersion(String protocol, long clientVersion) throws IOException {
    System.out.println("getProtocolVersion");
    return versionID;
  }

  public ProtocolSignature getProtocolSignature(String protocol, long clientVersion,
      int clientMethodsHash) throws IOException {
    // TODO Auto-generated method stub
    return new ProtocolSignature(TestRPC.TestProtocol.versionID, null);
  }

  public void sayhi() {
    System.out.println("hello");
  }

  public String payFor() {
    // TODO Auto-generated method stub
    return "10";
  }
  

}
