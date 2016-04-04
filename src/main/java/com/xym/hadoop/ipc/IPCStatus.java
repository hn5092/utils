package com.xym.hadoop.ipc;

import org.apache.hadoop.ipc.VersionedProtocol;

public interface IPCStatus extends VersionedProtocol{
  public static final long versionID = 1L;
  void sayhi();
  String payFor();
}
