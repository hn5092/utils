package test;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.junit.Test;

public class MultiInputIPCount {

	public static class IPCountMapper extends
			Mapper<Object, Text, keyTuple, LongWritable> {

		@Override
		protected void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

			Map<String, String> parsed = ParseLog.transformlineToMap(value
					.toString());

			String IP = parsed.get("IP");
			String CookieID = parsed.get("CookieID");

			keyTuple keytuple = new keyTuple(IP, CookieID);

			context.write(keytuple, new LongWritable(1));

		}
	}

	public static class IPCountReducer extends
			Reducer<keyTuple, LongWritable, keyTuple, LongWritable> {

		@Override
		protected void reduce(keyTuple key, Iterable<LongWritable> values,
				Context context) throws IOException, InterruptedException {

			long counter = 0;
			System.out.println(key);
			for (LongWritable val : values) {
				counter += val.get();
			}
			// 设置输出的最低阈值
			// if(counter>=10L)
			context.write(key, new LongWritable(counter));
		}

	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		// conf.set("mapreduce.client.submit.file.replication", "20");
		Job job = Job.getInstance(conf);
		job.setJarByClass(MultiInputIPCount.class);

		// 设置文件输入路径
		String path1 = "F:/test/data/";
		// 设置多数据源的输入路径 和各自的mapper函数
		job.setMapperClass(IPCountMapper.class);
		job.setMapOutputKeyClass(keyTuple.class);
		job.setMapOutputValueClass(LongWritable.class);
		FileInputFormat.setInputPaths(job, new Path(path1));
		// 路径设置
		Path outputDirIntermediate = new Path("F:/test/output5");
		// set reducer's property
		job.setReducerClass(IPCountReducer.class);
		job.setOutputKeyClass(keyTuple.class);
		job.setOutputValueClass(LongWritable.class);
		FileOutputFormat.setOutputPath(job, outputDirIntermediate);

		// submit
		// job.waitForCompletion(true);

		int code = job.waitForCompletion(true) ? 0 : 1;

	}

	// 自定义数据类型1
	public static class keyTuple implements WritableComparable<keyTuple> {

		private String IP;
		private String CookieID;

		public keyTuple() {
		}

		public keyTuple(String IP, String CookieID) {
			super();
			this.IP = IP;
			this.CookieID = CookieID;
		}

		@Override
		public String toString() {
			return this.IP + "\t" + this.CookieID;
		}

		public void readFields(DataInput in) throws IOException {
			// TODO Auto-generated method stub
			this.IP = in.readUTF();
			this.CookieID = in.readUTF();

		}

		public void write(DataOutput out) throws IOException {
			// TODO Auto-generated method stub
			out.writeUTF(IP);
			out.writeUTF(CookieID);
		}

		public int compareTo(keyTuple o) {
			// TODO Auto-generated method stub
			// return 0 表示两个元素相同，会reduce时key相同，聚合
			// return 1 设置key的排序，此处无法设置value的排序
			int l = Integer.parseInt(this.IP.substring(0,this.IP.indexOf(".")-1));
			int lo = Integer.parseInt(o.getIP().substring(0,this.IP.indexOf(".")-1));

			if (this.toString().equals(o.toString())) {// 判断字符串是否相等用equals
														// 变量相等用==
														// System.out.println(0
														// + this.IP + " " +
														// this.CookieID);
				return 0;
			}else{
				return l-lo;
			} 
		}

		public void setIP(String iP) {
			this.IP = iP;
		}

		public String getIP() {
			return IP;
		}

		public String getCookieID() {
			return CookieID;
		}

		public void setCookieID(String cookieID) {
			this.CookieID = cookieID;
		}

	}

	@Test
	public void test() {
		keyTuple k1 = new keyTuple("61.134.102.230",
				"353e3d477d7ecaeb089532b4ed4b005d");
		keyTuple k2 = new keyTuple("61.134.102.230",
				"353e3d477d7ecaeb089532b4ed4b005d");
		System.out.println(k1.compareTo(k2));
	}

	// 自定义数据类型2

}
