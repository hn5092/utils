package com.xym.hadoop.asyncdispatcher;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.TaskType;
import org.apache.hadoop.service.CompositeService;
import org.apache.hadoop.service.Service;
import org.apache.hadoop.yarn.event.AsyncDispatcher;
import org.apache.hadoop.yarn.event.Dispatcher;
import org.apache.hadoop.yarn.event.EventHandler;

public class SimpleMaster extends CompositeService {
	private Dispatcher dispatcher; // 中央异步调度器
	private String jobId;
	private int taskNumber; // 改作业包含的任务数目
	private String[] taskIDs; // 改作业包含的所有任务

	public SimpleMaster(String name, String jobID, int taskNumber) {
		super(name);
		this.jobId = jobID;
		this.taskNumber = taskNumber;
		taskIDs = new String[taskNumber];
		for (int i = 0; i < taskNumber; i++) {
			taskIDs[i] = new String(jobID + "_task_" + i);
		}

	}

	public void serviceInit(final Configuration conf) throws Exception {
		//定义一个中央异步调度器
		dispatcher = new AsyncDispatcher();
		//分别注册job和task事件调度器
		dispatcher.register(JobEventType.class, new JobEventDispatcher());
		dispatcher.register(TaskEventType.class, new TaskEventDispatcher());
		addService((Service) dispatcher);
		super.serviceInit(conf);
	}
private class JobEventDispatcher implements EventHandler<JobEvent>{

	@Override
	public void handle(JobEvent event) {
		if(event.getType() == JobEventType.JOB_KILL){
			System.out.println("receive JOB KILEE Event kill all tasks");
			for(int i = 0 ;i< taskNumber ; i++){
				dispatcher.getEventHandler().handle(new TaskEvent(taskIDs[i], TaskEventType.T_KILL));
			}
			
		}else if(event.getType() == JobEventType.JOB_INIT) {  
	        System.out.println("Receive JOB_INIT event, scheduling tasks");  
	        for(int i = 0; i < taskNumber; i++) {  
	            dispatcher.getEventHandler().handle(new TaskEvent(taskIDs[i],  
	               TaskEventType.T_SCHEDULE));  
	         }  
	      }  
	}
	
}
private class TaskEventDispatcher implements EventHandler<TaskEvent>{

	@Override
	public void handle(TaskEvent event) {
		 if(event.getType() == TaskEventType.T_KILL) {  
		        System.out.println("Receive T_KILL event of task " + event.getTaskID());  
		      } else if(event.getType() == TaskEventType.T_SCHEDULE) {  
		        System.out.println("Receive T_SCHEDULE event of task " + event.getTaskID());  
		      }  
	}
	
}
}
