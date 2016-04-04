package com.xym.hadoop.asyncdispatcher;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.service.Service;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.event.Dispatcher;

public class TestDAsyncDispatcher {
	public static void main(String[] args) throws Exception {
		String jobID = "job_20131215_12";
		SimpleMaster appMaster = new SimpleMaster("Simple MRAppMaster", jobID,
				5);
		YarnConfiguration conf = new YarnConfiguration(new Configuration());
		appMaster.serviceInit(conf);

		Service service = appMaster.getServices().get(0);
		service.start();
		Dispatcher dispatcher = (Dispatcher) service;
		dispatcher.getEventHandler().handle(
				new JobEvent(jobID, JobEventType.JOB_KILL));
		dispatcher.getEventHandler().handle(
				new JobEvent(jobID, JobEventType.JOB_INIT));
	}
}
