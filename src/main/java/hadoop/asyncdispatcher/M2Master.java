package hadoop.asyncdispatcher;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.service.CompositeService;
import org.apache.hadoop.service.Service;
import org.apache.hadoop.yarn.event.AsyncDispatcher;
import org.apache.hadoop.yarn.event.Dispatcher;

/**
 * 这是状态机的注册 任务,跟前面一样,但是需要添加的是这里需要把调度器放进状态机内部
 * @author imad
 *
 */
public class M2Master extends CompositeService {
	private Dispatcher dispatcher; // 中央异步调度器
	private String jobId;
	private int taskNumber; // 改作业包含的任务数目
	private String[] taskIDs; // 改作业包含的所有任务

	public M2Master(String name, String jobID, int taskNumber) {
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
		dispatcher.register(Event2.class, new M2(dispatcher.getEventHandler()));
		//分别注册job和task事件调度器
		addService((Service) dispatcher);
		super.serviceInit(conf);
	}

}
