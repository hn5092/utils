package com.xym.hadoop.rpc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmoteClient {
  public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
    RMIQuery rmiQuery = (RMIQuery) Naming.lookup("rmi://127.0.0.1:12090/query");
    rmiQuery.sayhi();
  }
}
