package hadoop.yarn.yarnapp;

import java.util.List;

import org.apache.hadoop.yarn.api.records.Container;
import org.apache.hadoop.yarn.api.records.ContainerStatus;
import org.apache.hadoop.yarn.api.records.NodeReport;
import org.apache.hadoop.yarn.client.api.async.AMRMClientAsync.CallbackHandler;

public class MyCallbackHandle implements CallbackHandler{

	public void onContainersCompleted(List<ContainerStatus> statuses) {
		// TODO Auto-generated method stub
		
	}

	public void onContainersAllocated(List<Container> containers) {
		// TODO Auto-generated method stub
		
	}

	public void onShutdownRequest() {
		// TODO Auto-generated method stub
		
	}

	public void onNodesUpdated(List<NodeReport> updatedNodes) {
		// TODO Auto-generated method stub
		
	}

	public float getProgress() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void onError(Throwable e) {
		// TODO Auto-generated method stub
		
	}

}
