package com.xym.hadoop.yarnrpc;

import java.io.IOException;

import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.server.api.ResourceTracker;
import org.apache.hadoop.yarn.server.api.protocolrecords.NodeHeartbeatRequest;
import org.apache.hadoop.yarn.server.api.protocolrecords.NodeHeartbeatResponse;
import org.apache.hadoop.yarn.server.api.protocolrecords.RegisterNodeManagerRequest;
import org.apache.hadoop.yarn.server.api.protocolrecords.RegisterNodeManagerResponse;

public class MyService implements ResourceTracker{
	
	@Override
	public RegisterNodeManagerResponse registerNodeManager(
			RegisterNodeManagerRequest request) throws YarnException,
			IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeHeartbeatResponse nodeHeartbeat(NodeHeartbeatRequest request)
			throws YarnException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
