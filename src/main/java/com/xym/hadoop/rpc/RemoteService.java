package com.xym.hadoop.rpc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RemoteService {
  public static void main(String[] args) throws RemoteException, MalformedURLException {
    RemoteObj remoteObj = new RemoteObj();
    LocateRegistry.createRegistry(12090);
    Naming.rebind("rmi://127.0.0.1:12090/query", remoteObj);
    System.out.println("服务启动完毕");
  }
}
