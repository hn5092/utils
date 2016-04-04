package xym.hbase.cc_auto;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MR {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(MR.class);
		job.setMapperClass(StepMapper.class);
		Path[] a = new Path[args.length - 1];
		for (int i = 0; i < (args.length - 1); i++) {
			a[i] = new Path(args[i]);
		}
		FileInputFormat.setInputPaths(job, a);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(NullWritable.class);
		FileOutputFormat.setOutputPath(job, new Path(args[args.length-1]));
		job.waitForCompletion(true);

	}

	public static class StepMapper extends
			Mapper<LongWritable, Text, NullWritable, NullWritable> {
		Configuration conf;
		HTable table;

		protected void setup(
				Mapper<LongWritable, Text, NullWritable, NullWritable>.Context context)
				throws IOException, InterruptedException {
			Configuration conf = HBaseConfiguration.create();
			conf.set("hbase.zookeeper.quorum",
					"xym01:2181,xym02:2181,xym03:2181");
			table = new HTable(conf, "auto");
			table.setAutoFlushTo(false);
		}

		public static final String COUNT = "auto";
		BeanInfo b = null;
		int count = 0;

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			b = BeanInfo.filterIP(value.toString());
			if (b.isValid()) {
				Put put = new Put(Bytes.toBytes(count+""));
				put.add(Bytes.toBytes("info"), Bytes.toBytes("time"),
						Bytes.toBytes(b.getTime()));
				put.add(Bytes.toBytes("info"), Bytes.toBytes("action"),
						Bytes.toBytes(b.getAction()));
				put.add(Bytes.toBytes("info"), Bytes.toBytes("ip"),
						Bytes.toBytes(b.getIp()));
				put.add(Bytes.toBytes("info"), Bytes.toBytes("screen"),
						Bytes.toBytes(b.getScreen()));
				put.add(Bytes.toBytes("info"), Bytes.toBytes("URL"),
						Bytes.toBytes(b.getURL()));
				put.add(Bytes.toBytes("info"), Bytes.toBytes("fromURL"),
						Bytes.toBytes(b.getFromURL()));
				table.put(put);
				count++;
				if (count % 100000 == 0){
					table.flushCommits();
				}
				context.write(NullWritable.get(), NullWritable.get());
			}
		}
		@Override
		protected void cleanup(
				Mapper<LongWritable, Text, NullWritable, NullWritable>.Context context)
					throws IOException, InterruptedException {
			table.flushCommits();
			table.close();
		}
		
	}

}
