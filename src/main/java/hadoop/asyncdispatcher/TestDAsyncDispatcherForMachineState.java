package hadoop.asyncdispatcher;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.service.Service;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.event.Dispatcher;
import org.junit.Test;

public class TestDAsyncDispatcherForMachineState {
	/**
	 * 测试单个状态机
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String jobID = "job_20131215_12";
		String jobID2 = "job_20131215_12";

		MachineStateMast machineStateMast = new MachineStateMast(
				"Simple MRAppMaster", jobID, 5);
		YarnConfiguration conf = new YarnConfiguration(new Configuration());
		machineStateMast.serviceInit(conf);
		// 因为这里是多服务,所以这里得到的server 是需要get出来的 如果较多服务直接使用for循环启动
		Service service = machineStateMast.getServices().get(0);
		service.start();
		// 因为add服务的时候是 add一个object对象!!!!!! 拿出来的时候是要自己转型的
		Dispatcher dispatcher = (Dispatcher) service;
		// 通过调度器启动事件
		JobEvent event = new JobEvent(jobID, JobEventType.JOB_INIT);
		dispatcher.getEventHandler().handle(event);
		// stateMachine.doTransition(event.getType(), event);

	}

	/**
	 * 测试多个状态机联用
	 * 
	 * @throws Exception
	 */
	@Test
	public void tesM1toM2() throws Exception {
		String jobID = "job_111";
		M1Master machineStateMast = new M1Master("Simple MRAppMaster", jobID, 5);
		YarnConfiguration conf = new YarnConfiguration(new Configuration());
		machineStateMast.serviceInit(conf);
		// 因为这里是多服务,所以这里得到的server 是需要get出来的 如果较多服务直接使用for循环启动
		Service service = machineStateMast.getServices().get(0);
		service.start();
		// 因为这里是多服务,所以这里得到的server 是需要get出来的 如果较多服务直接使用for循环启动
		Dispatcher dispatcher = (Dispatcher) service;
		job1 event1 = new job1(jobID, Event1.JOB_INIT);
		dispatcher.getEventHandler().handle(event1);
		// Dispatcher dispatcher2 = (Dispatcher) service2;
		// job2 event2 = new job2(jobID, Event2.JOB_INIT);
		// dispatcher2.getEventHandler().handle(event2);
	}
}
