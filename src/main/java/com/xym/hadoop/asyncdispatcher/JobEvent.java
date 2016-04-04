package com.xym.hadoop.asyncdispatcher;

import org.apache.hadoop.yarn.event.AbstractEvent;

public class JobEvent extends AbstractEvent<JobEventType>{
	private String jobID;  
	public JobEvent(String jobID, JobEventType type) {
		super(type);
		this.jobID = jobID;
		// TODO Auto-generated constructor stub
	}
	public String getJobId(){
		return jobID;
	}

}
