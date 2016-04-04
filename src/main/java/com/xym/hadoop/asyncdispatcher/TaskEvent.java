package com.xym.hadoop.asyncdispatcher;

import org.apache.hadoop.yarn.event.AbstractEvent;

public class TaskEvent extends AbstractEvent<TaskEventType>{

	private String taskID; //Task ID  
	public TaskEvent(String taskID, TaskEventType type) {
		super(type);
		this.taskID = taskID;
	}
	public String getTaskID() {
		return taskID;
	}
	

}
