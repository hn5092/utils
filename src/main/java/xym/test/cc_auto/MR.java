package xym.test.cc_auto;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MR {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(MR.class);
//		DistributedCache.addCacheFile(new URI(uriWithLink), conf);
//		job.addCacheArchive(uri);
		job.setMapperClass(StepMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));

		job.setReducerClass(StepReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);

	}

	public static class StepMapper extends
			Mapper<LongWritable, Text, Text, Text> {
		public static final String COUNT = "auto";
		Text k2 = new Text();
		Text v2 = new Text();
		BeanInfo b = null;
		@Override
		protected void setup(
				Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
//			URI[] cacheFiles = context.get
		}
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			b = BeanInfo.filterIP(value.toString());
			if(b.isValid()){
				k2.set(b.getURL());
				v2.set(b.getIp());
			context.write(k2, v2);
			}	
		}

	}

	// 1,[103:2.5,101:5.0,102:3.0]
	public static class StepReducer extends Reducer<Text, Text, Text, Text> {
		private Map<String, Integer> map = new HashMap<String, Integer>();
		private Text v3 = new Text();
		Set<Text> tSet = new HashSet<Text>();
		@Override
		protected void reduce(Text k2, Iterable<Text> v2,
				Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
				for(Text t : v2){
					tSet.add(t);
				}
				v3.set(tSet.size()+"");
				
				context.write(k2,v3);
		}

	}
}
