package hadoop.asyncdispatcher;

import org.apache.hadoop.yarn.event.AbstractEvent;

public class job2 extends AbstractEvent<Event2>{
	private String jobID;  
	public job2(String jobID, Event2 type) {
		super(type);
		this.jobID = jobID;
		// TODO Auto-generated constructor stub
	}
	public String getJobId(){
		return jobID;
	}

}
