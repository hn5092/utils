package xym.hbase.mr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 这个mapreduce通过读取hbase auto 的数据然后倒入hbase test 里面
 * 
 * @author imad
 *
 */
public class MRForHbase2 {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println(start);
		Configuration con = new Configuration();
		Configuration conf = HBaseConfiguration.create(con);
		conf.set("hbase.zookeeper.quorum", "xym01:2181,xym02:2181,xym03:2181");
		try {
			Job job = Job.getInstance(conf);
			job.setJarByClass(MRForHbase.class);
			// 测试专用
			// 多个false.......................................................挪到集群上就不需要了
			TableMapReduceUtil.initTableMapperJob("auto",
					new Scan(Bytes.toBytes("00000"), Bytes.toBytes("51001")),
					MyHMap.class, Text.class, NullWritable.class, job, false);
			TableMapReduceUtil.initTableReducerJob("test", MyReduce.class, job,
					null, null, null, null, false);
			FileOutputFormat.setOutputPath(job, new Path(args[0]));
			long end = System.currentTimeMillis();
			try {
				int result = job.waitForCompletion(true) ? 0 : 1;
				if (result == 0) {
					System.out.println(end - start);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class MyHMap extends TableMapper<Text, NullWritable> {
		@Override
		protected void map(
				ImmutableBytesWritable key,
				Result value,
				Mapper<ImmutableBytesWritable, Result, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			context.write(new Text(value.toString()), NullWritable.get());

		}
	}

	public static class MyReduce extends TableReducer<Text, Text, NullWritable> {
		int count = 0;

		@Override
		protected void reduce(Text key, Iterable<Text> value,
				Reducer<Text, Text, NullWritable, Mutation>.Context context)
				throws IOException, InterruptedException {
			count++;
			Put put = new Put(Bytes.toBytes(count+""));
			put.add(Bytes.toBytes("info"), Bytes.toBytes("test"),
					Bytes.toBytes(key.toString()));
			context.write(NullWritable.get(), put);
		}
	}

}
