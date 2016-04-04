package com.xym.hadoop.rpc;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIQuery extends Remote{
  void sayhi() throws RemoteException;

}
