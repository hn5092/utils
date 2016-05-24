package storm;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 接收喷发节点(Spout)发送的数据进行简单的处理后，发射出去。
 *
 * @author Administrator
 */
@SuppressWarnings("serial")
public class SimpleBolt extends BaseBasicBolt {
    @Override
    public void prepare(Map stormConf, TopologyContext context) {

    }

    public void execute(Tuple input, BasicOutputCollector collector) {
        try {
            String msg = input.getString(0);
            if (msg != null) {
                //System.out.println("msg="+msg);
                collector.emit(new Values(msg));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("info"));
    }

}