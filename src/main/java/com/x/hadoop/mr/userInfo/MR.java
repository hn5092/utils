package com.x.hadoop.mr.userInfo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.TreeList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.SequenceFile.Metadata;
import org.apache.hadoop.mapred.lib.ChainMapper;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.bloom.CountingBloomFilter;
/**
 * 通过 登陆时间和用户ID 分析出 第一次登陆时间和最后一次登陆时间,登陆次数
 * 计算出信息之后再根据登陆次数排序
 * step 1. 70	2015-05-16 07:47:24-->0|2014-10-09 00:30:35|2015-07-12 10:50:59|9
 * step 2. 0|2014-10-09 00:30:35|2015-07-12 10:50:59|9 -->333|2014-09-23 09:50:56|2015-09-04 03:27:20|21
 * @author imad
 *
 */

public class MR {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(MR.class);

		job.setMapperClass(StepMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
//	ChainMapper.addMapper(job, klass, inputKeyClass, inputValueClass, outputKeyClass, outputValueClass, byValue, mapperConf);
		job.setReducerClass(StepReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		int result = job.waitForCompletion(true) ? 0 : 1;
		if (result == 0) {
			Configuration conf2 = new Configuration();
			Job job2 = Job.getInstance(conf2);
			job2.setJarByClass(MR.class);

			job2.setMapperClass(StepMapper2.class);
			job2.setMapOutputKeyClass(LongWritable.class);
			job2.setMapOutputValueClass(Text.class);
			FileInputFormat.setInputPaths(job2, new Path(args[1]));

			job2.setReducerClass(StepReducer2.class);
			job2.setOutputKeyClass(Text.class);
			job2.setOutputValueClass(NullWritable.class);
			FileOutputFormat.setOutputPath(job2, new Path(args[2]));
			job2.waitForCompletion(true);
		}

	}

	public static class StepMapper extends
			Mapper<LongWritable, Text, Text, LongWritable> {
		public static final String COUNT = "auto";
		Text k2 = new Text();
		LongWritable v2 = new LongWritable();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, String> Metadata = new HashMap<String, String>();

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			Map<String, String> info = parseData(value.toString());
			try {
				Date d = sdf.parse(info.get("TIME"));
				k2.set(info.get("ID"));
				v2.set(d.getTime());
				context.write(k2, v2);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		private Map<String, String> parseData(String value) {
			Metadata.clear();
			String[] data = value.split("\t");
			Metadata.put("ID", data[0]);
			Metadata.put("TIME", data[1]);
			return Metadata;
		}

		@Override
		protected void cleanup(
				Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			Metadata.clear();
			sdf = null;
			k2 = null;
			v2 = null;
		}
	}

	public static class StepReducer extends
			Reducer<Text, LongWritable, Text, NullWritable> {
		private Text k3 = new Text();
		List<Long> t = new ArrayList<Long>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		@Override
		protected void reduce(Text k2, Iterable<LongWritable> v2,
				Reducer<Text, LongWritable, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			t.clear();
			Iterator<LongWritable> value = v2.iterator();
			while (value.hasNext()) {
				LongWritable l = value.next();
				t.add(Long.parseLong(l.toString()));
			}
			t.sort(null);
			String frist = sdf.format(new Date(t.get(0)));
			String last = sdf.format(new Date(t.get(t.size() - 1)));
			String count = t.size() + "";
			k3.set(k2.toString() + " " + frist + " " + last + " " + count);
			context.write(k3, NullWritable.get());
		}

		@Override
		protected void cleanup(
				Reducer<Text, LongWritable, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			k3 = null;
			t.clear();
			sdf = null;
		}
	}

	public static class StepMapper2 extends
			Mapper<LongWritable, Text, LongWritable, Text> {
		Map<String, String> Metadata = new HashMap<String, String>();
		LongWritable i = new LongWritable();

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			i.set(-Long.parseLong(value.toString().split(" ")[5]));
			context.write(i, value);
		}

		@Override
		protected void cleanup(
				Mapper<LongWritable, Text, LongWritable, Text>.Context context)
				throws IOException, InterruptedException {
			i = null;
		}
	}

	public static class StepReducer2 extends
			Reducer<LongWritable, Text, Text, NullWritable> {
		private Text k3 = new Text();

		protected void reduce(LongWritable k2, Iterable<Text> v2,
				Reducer<LongWritable, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String data = "";
			context.getCounter("hah", "go").increment(1);
			for (Text t : v2) {
				data +=k2.toString()+ " "+ t.toString()
						.substring(0, t.toString().lastIndexOf(" ")) + "\n";
			}
			data = data.substring(0, data.lastIndexOf("\n"));
			k3.set(data);
			context.write(k3, NullWritable.get());
		}

	}
}
