package com.xym.hadoop.asyncdispatcher;

import java.util.EnumSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.hadoop.yarn.event.EventHandler;
import org.apache.hadoop.yarn.state.StateMachine;
import org.apache.hadoop.yarn.state.StateMachineFactory;

public class JobStateMachine implements EventHandler<JobEvent> {
	private final String jobID;
	private EventHandler eventHandler;
	private final Lock writeLock;
	private final Lock readLock;
	StateMachine<JobStateInternal, JobEventType, JobEvent> stateMachine;
	protected static final StateMachineFactory<JobStateMachine, JobStateInternal, JobEventType, JobEvent> STATE_MACHINE_FACTORY = new StateMachineFactory<JobStateMachine, JobStateInternal, JobEventType, JobEvent>(
			JobStateInternal.NEW)
			.addTransition(JobStateInternal.NEW, JobStateInternal.INITED,
					JobEventType.JOB_INIT, new InitTransition())
			.addTransition(JobStateInternal.INITED, JobStateInternal.SETUP,
					JobEventType.JOB_START, new StartTransition())
			.addTransition(JobStateInternal.SETUP, JobStateInternal.RUNNING,
					JobEventType.JOB_SETUP_COMPLETED,
					new SetupCompletedTransition())
			.addTransition(
					JobStateInternal.RUNNING,
					EnumSet.of(JobStateInternal.KILLED,
							JobStateInternal.SUCCEEDED),
					JobEventType.JOB_COMPLETED,
					new JobTasksCompletedTransition()).installTopology();

	public JobStateMachine(String jobID, EventHandler eventHandler) {
		this.jobID = jobID;
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		this.readLock = readWriteLock.readLock();
		this.writeLock = readWriteLock.writeLock();
		this.eventHandler = eventHandler;
		this.stateMachine = STATE_MACHINE_FACTORY.make(this);
	}

	protected StateMachine<JobStateInternal, JobEventType, JobEvent> getStateMachine() {
		return stateMachine;
	}

	@Override
	public void handle(JobEvent event) {
	}
}
