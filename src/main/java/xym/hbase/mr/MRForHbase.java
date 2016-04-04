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
 * 测试从map中读取表数据
 * @author imad
 *
 */
public class MRForHbase {
	public static void main(String[] args) {
		Configuration con = new Configuration();
		Configuration conf = HBaseConfiguration.create(con);
		conf.set("hbase.zookeeper.quorum", "xym01:2181,xym02:2181,xym03:2181");
		try {
			Job job = Job.getInstance(conf);
			job.setJarByClass(MRForHbase.class);
			// 测试专用
			// 多个false.......................................................挪到集群上就不需要了
			TableMapReduceUtil.initTableMapperJob("auto",
					new Scan(Bytes.toBytes("10000"), Bytes.toBytes("10001")),
					MyHMap.class, Text.class, Text.class, job, false);
			FileOutputFormat.setOutputPath(job, new Path(args[0]));
			try {
				System.exit(job.waitForCompletion(true) ? 0 : 1);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class MyHMap extends TableMapper<Text, Text> {
		@Override
		protected void map(
				ImmutableBytesWritable key,
				Result value,
				Mapper<ImmutableBytesWritable, Result, Text, Text>.Context context)
				throws IOException, InterruptedException {
			System.out.println(key);
			System.out.println(value);
//			context.write(new Text(key.toString()), new Text(key.toString()));

		}
	}

	public static class MyHReduce extends
			TableReducer<Text, Text, NullWritable> {
		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1,
				Reducer<Text, Text, NullWritable, Mutation>.Context arg2)
				throws IOException, InterruptedException {
			
			
			arg2.write(NullWritable.get(), new Put(Bytes.toBytes(11)));
		}
	}
}
