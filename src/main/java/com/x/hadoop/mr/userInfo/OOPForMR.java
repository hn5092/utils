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
import org.apache.hadoop.mapred.MultiFileInputFormat;
import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.bloom.CountingBloomFilter;

public class OOPForMR {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(OOPForMR.class);

		job.setMapperClass(StepMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));

		job.setReducerClass(StepReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		int result = job.waitForCompletion(true) ? 0 : 1;
		if (result == 0) {
			Configuration conf2 = new Configuration();
			Job job2 = Job.getInstance(conf2);
			job2.setJarByClass(OOPForMR.class);
//			MultipleInputs.addInputPath(conf, path, inputFormatClass, mapperClass);
			job2.setMapperClass(StepMapper2.class);
			job2.setMapOutputKeyClass(LongWritable.class);
			job2.setMapOutputValueClass(UserInfo2.class);
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
		UserInfo userInfo = null;
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			try {
				userInfo = UserInfo.getInstance(value.toString());
				k2.set(userInfo.getUSERID());
				v2.set(userInfo.getDate());
				context.write(k2, v2);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}


		@Override
		protected void cleanup(
				Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			k2 = null;
			v2 = null;
			userInfo = null;
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
			k3.set(k2.toString() + "|" + frist + "|" + last + "|" + count);
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
			Mapper<LongWritable, Text, LongWritable, UserInfo2> {
		LongWritable i = new LongWritable();
		UserInfo2 u = null;
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			try {
				u = UserInfo2.getInstance(value.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i.set(-u.getTimes());
			context.write(i, u);
		}

		@Override
		protected void cleanup(
				Mapper<LongWritable, Text, LongWritable, UserInfo2>.Context context)
				throws IOException, InterruptedException {
			i = null;
		}
	}

	public static class StepReducer2 extends
			Reducer<LongWritable, UserInfo2, Text, NullWritable> {
		private Text k3 = new Text();

		protected void reduce(LongWritable k2, Iterable<UserInfo2> v2,
				Reducer<LongWritable, UserInfo2, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String data = "";
			for (UserInfo2 u : v2) {
				data +=  u.toString() + "\n";
			}
			data = data.substring(0, data.lastIndexOf("\n"));
			k3.set(data);
			context.write(k3, NullWritable.get());
		}

	}
}
