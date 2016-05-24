package storm.wc;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class Main {

  public static void main(String[] args) throws Exception {
    TopologyBuilder builder = new TopologyBuilder();
    builder.setSpout("word-reader", new ParallelFileSpout());
    builder.setBolt("word-normalizer", new DetectionBolt(), 1).fieldsGrouping("word-reader",
        new Fields("word1"));
    builder.setBolt("word-counter", new CountBolt()).fieldsGrouping("word-normalizer",
        new Fields("word"));
    Config conf = new Config();
    StormSubmitter.submitTopology("wordCounterTopology", conf, builder.createTopology());
  }

}
