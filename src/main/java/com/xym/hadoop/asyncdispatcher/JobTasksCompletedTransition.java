package com.xym.hadoop.asyncdispatcher;

import org.apache.hadoop.yarn.state.MultipleArcTransition;

public class JobTasksCompletedTransition implements
		MultipleArcTransition<JobStateMachine, JobEvent, JobStateInternal> {

	@Override
	public JobStateInternal transition(JobStateMachine operand, JobEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

}
