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
 * 这是一个状态机,这个状态机用来控制job1的整个运行流程
 * @author imad
 *
 */
public class M1 implements EventHandler<job1> {
	// private final String jobId;
	private EventHandler eventHandler;
	private final Lock writeLock;
	private final Lock readLock;
	private static StateMachine<JobStateInternal, Event1, job1> stateMachine;
	/**
	 * 构建一个状态机的DAG   这个DAG中分别分开始状态 结束状态  job事件
	 */
	private static final StateMachineFactory<M1, JobStateInternal, Event1, job1> STATE_MACHINE_FACTORY = new StateMachineFactory<M1, JobStateInternal, Event1, job1>(
			JobStateInternal.NEW)
			//忽略的事件  这个是源码中没引用  但是没删除的代码
//			.addTransition(JobStateInternal.CREATE, JobStateInternal.NEW,
//					Event1.JOB_PREPARE)
			//这里 的job_INIT任务事件同时用了2次, 可以体现状态机的细分任务的作用
			.addTransition(JobStateInternal.NEW, JobStateInternal.INITED,
					Event1.JOB_INIT, new InitTransition())
			.addTransition(JobStateInternal.INITED, JobStateInternal.SETUP,
					Event1.JOB_INIT, new StartInitTransition())
			.addTransition(JobStateInternal.SETUP, JobStateInternal.RUNNING,
					Event1.JOB_START, new StartTransition())
			.addTransition(JobStateInternal.RUNNING, JobStateInternal.SUCCEEDED,
					Event1.JOB_SETUP_COMPLETED,
					new SetupCompletedTransition())
			.addTransition(
					JobStateInternal.SUCCEEDED,
					//如果使用enumset的话,需要继承的MultipleArcTransition  返回值就是类型
					EnumSet.of(JobStateInternal.KILLED,
							JobStateInternal.SUCCEEDED),
					Event1.JOB_COMPLETED,
					new JobTasksCompletedTransition()).installTopology();

	// public JobStateMachine(String jobId, EventHandler eventHandler) {
	// this.jobId = jobId;
	// ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	// this.readLock = readWriteLock.readLock();
	// this.writeLock = readWriteLock.writeLock();
	// this.eventHandler = eventHandler;
	// this.stateMachine = STATE_MACHINE_FACTORY.make(this);
	// }
	public M1(EventHandler eventHandler) {
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		this.readLock = readWriteLock.readLock();
		this.writeLock = readWriteLock.writeLock();
		this.eventHandler = eventHandler;
		this.stateMachine = STATE_MACHINE_FACTORY.make(this);
	}

	protected static StateMachine<JobStateInternal, Event1, job1> getStateMachine() {
		return stateMachine;
	}	
	/**
	 * 调用handle方法 转换每一个事件 一次调用  
	 */
	public void handle(job1 event) {
		try {
			writeLock.lock();
			JobStateInternal oldState = getInternalState();
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
			SingleArcTransition<M1, job1> {

		public void transition(M1 operand, job1 event) {
			System.out.println("job 1 1.......");
			operand.eventHandler.handle(new job1(event.getJobId(),
					Event1.JOB_INIT));
		}
	}

	static private class StartInitTransition implements
			SingleArcTransition<M1, job1> {

		public void transition(M1 operand, job1 event) {
			System.out.println("job  1 2 ...... 切到 M2 ");
			operand.eventHandler.handle(new job2(event.getJobId(), Event2.JOB_INIT));
		}
	}

	static private class StartTransition implements
			SingleArcTransition<M1, job1> {

		public void transition(M1 operand, job1 event) {
			System.out.println("job 1 3 .......");
			operand.eventHandler.handle(new job1(event.getJobId(),
					Event1.JOB_SETUP_COMPLETED));
		}
	}

	static private class SetupCompletedTransition implements
			SingleArcTransition<M1, job1> {

		public void transition(M1 operand, job1 event) {
			System.out.println("job 1  4 .......");
			operand.eventHandler.handle(new job1(event.getJobId(),
					Event1.JOB_COMPLETED));
		}
	}

	static public class JobTasksCompletedTransition implements
			MultipleArcTransition<M1, job1, JobStateInternal> {

		public JobStateInternal transition(M1 operand,
				job1 event) {
			System.out.println("job 1 5 .......继续切M2");
			operand.eventHandler.handle(new job2("job_111", Event2.JOB_START));

			return null;
		}

	}

}
