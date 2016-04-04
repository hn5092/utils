package com.xym.hadoop.asyncdispatcher;

import org.apache.hadoop.yarn.state.SingleArcTransition;

public class InitTransition implements
		SingleArcTransition<JobStateMachine, JobEvent> {

	@Override
	public void transition(JobStateMachine operand, JobEvent event) {
		
	}

}
