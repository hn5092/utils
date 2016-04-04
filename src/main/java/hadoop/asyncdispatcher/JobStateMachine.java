package hadoop.asyncdispatcher;

import java.util.EnumSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.hadoop.yarn.event.EventHandler;
import org.apache.hadoop.yarn.state.InvalidStateTransitonException;
import org.apache.hadoop.yarn.state.MultipleArcTransition;
import org.apache.hadoop.yarn.state.SingleArcTransition;
import org.apache.hadoop.yarn.state.StateMachine;
import org.apache.hadoop.yarn.state.StateMachineFactory;
/**
 * 这是一个状态机,这个状态机用来控制jobevent的整个运行流程
 * @author imad
 *
 */
public class JobStateMachine implements EventHandler<JobEvent> {
	// private final String jobId;
	private EventHandler<JobEvent> eventHandler;
	private final Lock writeLock;
	private final Lock readLock;
	static StateMachine<JobStateInternal, JobEventType, JobEvent> stateMachine;
	/**
	 * 构建一个状态机的DAG   这个DAG中分别分开始状态 结束状态  job事件
	 */
	protected static final StateMachineFactory<JobStateMachine, JobStateInternal, JobEventType, JobEvent> STATE_MACHINE_FACTORY = new StateMachineFactory<JobStateMachine, JobStateInternal, JobEventType, JobEvent>(
			JobStateInternal.NEW)
			//忽略的事件  这个是源码中没引用  但是没删除的代码
//			.addTransition(JobStateInternal.CREATE, JobStateInternal.NEW,
//					JobEventType.JOB_PREPARE)
			//这里 的job_INIT任务事件同时用了2次, 可以体现状态机的细分任务的作用
			.addTransition(JobStateInternal.NEW, JobStateInternal.INITED,
					JobEventType.JOB_INIT, new InitTransition())
			.addTransition(JobStateInternal.INITED, JobStateInternal.SETUP,
					JobEventType.JOB_INIT, new StartInitTransition())
			.addTransition(JobStateInternal.SETUP, JobStateInternal.RUNNING,
					JobEventType.JOB_START, new StartTransition())
			.addTransition(JobStateInternal.RUNNING, JobStateInternal.SUCCEEDED,
					JobEventType.JOB_SETUP_COMPLETED,
					new SetupCompletedTransition())
			.addTransition(
					JobStateInternal.SUCCEEDED,
					//如果使用enumset的话,需要继承的MultipleArcTransition  返回值就是类型
					EnumSet.of(JobStateInternal.KILLED,
							JobStateInternal.SUCCEEDED),
					JobEventType.JOB_COMPLETED,
					new JobTasksCompletedTransition()).installTopology();

	// public JobStateMachine(String jobId, EventHandler eventHandler) {
	// this.jobId = jobId;
	// ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	// this.readLock = readWriteLock.readLock();
	// this.writeLock = readWriteLock.writeLock();
	// this.eventHandler = eventHandler;
	// this.stateMachine = STATE_MACHINE_FACTORY.make(this);
	// }
	public JobStateMachine(EventHandler eventHandler) {
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		this.readLock = readWriteLock.readLock();
		this.writeLock = readWriteLock.writeLock();
		this.eventHandler = eventHandler;
		this.stateMachine = STATE_MACHINE_FACTORY.make(this);
	}

	protected static StateMachine<JobStateInternal, JobEventType, JobEvent> getStateMachine() {
		return stateMachine;
	}	
	/**
	 * 调用handle方法 转换每一个事件 一次调用  
	 */
	public void handle(JobEvent event) {
		try {
			writeLock.lock();
			JobStateInternal oldState = getInternalState();
			System.out.println("the oldstate is    " + oldState
					+ "   ..........");
			try {
				getStateMachine().doTransition(event.getType(), event);
			} catch (InvalidStateTransitonException e) {
			}
			// notify the eventhandler of state change
		}

		finally {
			writeLock.unlock();
		}
	}
	/**
	 * 得到当前的 状态机的事件
	 * @return
	 */
	private JobStateInternal getInternalState() {
		readLock.lock();
		try {
			return getStateMachine().getCurrentState();
		} finally {
			readLock.unlock();
		}
	}

	static private class InitTransition implements
			SingleArcTransition<JobStateMachine, JobEvent> {

		public void transition(JobStateMachine operand, JobEvent event) {
			System.out.println("job 1 .......");
			operand.eventHandler.handle(new JobEvent(event.getJobId(),
					JobEventType.JOB_INIT));
		}
	}

	static private class StartInitTransition implements
			SingleArcTransition<JobStateMachine, JobEvent> {

		public void transition(JobStateMachine operand, JobEvent event) {
			System.out.println("job   2 ......");
//			operand.eventHandler.handle(new JobEvent(event.getJobId(),
//					JobEventType.JOB_START));
		}
	}

	static private class StartTransition implements
			SingleArcTransition<JobStateMachine, JobEvent> {

		public void transition(JobStateMachine operand, JobEvent event) {
			System.out.println("job 3 .......");
			operand.eventHandler.handle(new JobEvent(event.getJobId(),
					JobEventType.JOB_SETUP_COMPLETED));
		}
	}

	static private class SetupCompletedTransition implements
			SingleArcTransition<JobStateMachine, JobEvent> {

		public void transition(JobStateMachine operand, JobEvent event) {
			System.out.println("job 4 .......");
			operand.eventHandler.handle(new JobEvent(event.getJobId(),
					JobEventType.JOB_COMPLETED));
		}
	}

	static public class JobTasksCompletedTransition implements
			MultipleArcTransition<JobStateMachine, JobEvent, JobStateInternal> {

		public JobStateInternal transition(JobStateMachine operand,
				JobEvent event) {
			System.out.println("job 5 .......");
			return null;
		}

	}

}
