package com.x.hadoop.mr.bbs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
	import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BBS {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(BBS.class);

		MultipleInputs.addInputPath(job, new Path(args[0]),
				TextInputFormat.class, StepMapper.class);
		MultipleInputs.addInputPath(job, new Path(args[1]),
				TextInputFormat.class, Step2Mapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setReducerClass(StepReducer.class);
		job.setOutputKeyClass(GBKOutputFormat.class);
		job.setOutputValueClass(NullWritable.class);
		//设置GBK 模式的
		job.setOutputFormatClass(GBKOutputFormat.class);
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		job.waitForCompletion(true);
		job.getCounters().findCounter("1","1").getValue();

	}
	public static String transformTextToUTF8(Text text, String encoding) {
		String value = null;
		try {
		value = new String(text.getBytes(), 0, text.getLength(), encoding);
		} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
		}
		return value;
		}

	public static class StepMapper extends
			Mapper<LongWritable, Text, Text, Text> {
		Text k2 = new Text();
		Text v2 = new Text();
		PostInfo p = null;

		@Override
		// 1 103:2.5,101:5.0,102:3.0
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			p = PostInfo.getInstance(transformTextToUTF8(value,"GBK"));
//			p = PostInfo.getInstance(value.toString());
			k2.set(p.getId());
			v2.set(p.toString());
			context.write(k2, v2);

		}

	}

	public static class Step2Mapper extends
			Mapper<LongWritable, Text, Text, Text> {
		Text k2 = new Text();
		Text v2 = new Text();

		BaseInfo b = null;

		@Override
		// 1 103:2.5,101:5.0,102:3.0
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			b = BaseInfo.getInstance(transformTextToUTF8(value,"GBK"));
//			b = BaseInfo.getInstance(value.toString());
			k2.set(b.getId());
			v2.set(b.toString());
			context.write(k2, v2);

		}

	}

	// 1,[103:2.5,101:5.0,102:3.0]
	public static class StepReducer extends
			Reducer<Text, Text, Text, NullWritable> {

		private Text v3 = new Text();
		String data = "";
		String id = "";

		@Override
		protected void reduce(Text k2, Iterable<Text> v2,
				Reducer<Text, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			data = k2.toString() + "|";
			id = null;
			for (Text t : v2) {
				String temp = t.toString();
				if (temp.startsWith("A")) {
					id = temp.substring(1, temp.length());
					break;
				}
			}
			for (Text t : v2) {
				String temp = t.toString();
				if (temp.startsWith("B")) {
					// 
					data = k2.toString()+ "|"+temp.substring(1, temp.length())+ "|" + id;
					v3.set(data);
					context.write(v3, NullWritable.get());
					data = null;
				}
			}

		}

	}
}
