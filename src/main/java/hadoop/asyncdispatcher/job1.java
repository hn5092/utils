package hadoop.asyncdispatcher;

import org.apache.hadoop.yarn.event.AbstractEvent;

public class job1 extends AbstractEvent<Event1>{
	private String jobID;  
	public job1(String jobID, Event1 type) {
		super(type);
		this.jobID = jobID;
		// TODO Auto-generated constructor stub
	}
	public String getJobId(){
		return jobID;
	}

}
