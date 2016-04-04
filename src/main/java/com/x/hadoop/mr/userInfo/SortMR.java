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
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.SequenceFile.Metadata;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.bloom.CountingBloomFilter;

public class SortMR {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(SortMR.class);

		job.setMapperClass(StepMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));

		job.setCombinerClass(StepCombiner.class);
		
		job.setReducerClass(StepReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);

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

	// 1,[103:2.5,101:5.0,102:3.0]
	public static class StepReducer extends
			Reducer<Text, LongWritable, Text, Text> {
		private Text v3 = new Text();
		List<Long> t = new ArrayList<Long>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		@Override
		protected void reduce(Text k2, Iterable<LongWritable> v2,
				Reducer<Text, LongWritable, Text, Text>.Context context)
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
			v3.set(frist + "|" + last + "|" + count);
			context.write(k2, v3);
		}

		@Override
		protected void cleanup(
				Reducer<Text, LongWritable, Text, Text>.Context context)
				throws IOException, InterruptedException {
			v3 = null;
			t.clear();
			sdf = null;
		}
	}

	public static class StepCombiner extends
			Reducer<Text, LongWritable, Text, Text> {
		private Text v3 = new Text();
		List<Long> t = new ArrayList<Long>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		@Override
		protected void reduce(Text k2, Iterable<LongWritable> v2,
				Reducer<Text, LongWritable, Text, Text>.Context context)
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
			v3.set(frist + "|" + last + "|" + count);
			context.write(k2, v3);
		}

		@Override
		protected void cleanup(
				Reducer<Text, LongWritable, Text, Text>.Context context)
				throws IOException, InterruptedException {
			v3 = null;
			t.clear();
			sdf = null;
		}
	}
}
