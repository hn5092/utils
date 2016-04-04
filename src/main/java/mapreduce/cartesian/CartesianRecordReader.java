//package com.x.hadoop.mr.cartesian;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapred.FileInputFormat;
//import org.apache.hadoop.mapred.Reporter;
//import org.apache.hadoop.mapreduce.InputSplit;
//import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.RecordReader;
//import org.apache.hadoop.mapreduce.TaskAttemptContext;
//import org.apache.hadoop.mapreduce.lib.join.CompositeInputSplit;
//import org.apache.hadoop.util.ReflectionUtils;
//
//import com.sun.tools.classfile.Opcode.Set;
//import com.sun.tools.hat.internal.parser.ReadBuffer;
//
///**
// * 该方法使用的是老的mapreduce 的 包， 新的包待看过源码之后在进行重写
// * 
// * @author imad
// *
// * @param <K1>
// * @param <K2>
// * @param <V1>
// * @param <V2>
// */
//public class CartesianRecordReader extends
//		RecordReader<Text, Text> {
//
//	private RecordReader<Text, Text> leftRR = null, rightRR = null;
//
//	private Configuration rightConf;
//	private InputSplit rIS;
//	private InputSplit lIS;
//	// help variables
//	private Text currentKey = new Text();
//	private Text currentvalue = new Text();
//	private boolean goToNextLeft = true, alldone = false;
//	
//	private BufferedReader lb = null;
//	@SuppressWarnings("unchecked")
//	public CartesianRecordReader(CompositeInputSplit split, Job job,
//			Reporter reporter) throws ClassNotFoundException, IOException {
//		this.rightConf = job.getConfiguration();
//		this.rIS = split.get(0);
//		this.lIS = split.get(1);
//		// Create left record reader
//		
//
//	}
//
//
//	@Override
//	public void initialize(InputSplit split,
//			TaskAttemptContext context) throws IOException,
//			InterruptedException {
//		new BufferedReader(new FileReader(new File(this.rIS.)));
//		new BufferedReader(new FileReader(new File("")));
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public boolean nextKeyValue() throws IOException, InterruptedException {
//		do{
//			if (goToNextLeft) {
//				//如果下一个key false 不存在 那么跳出循环
//				if(!leftRR.nextKeyValue()){
//					alldone = true;
//					break;
//				}else{
//					goToNextLeft = alldone = false;
//				}
//			}
//			if(rightRR.nextKeyValue()){
//				value.set(value.toString());
//				
//			}else{
//				goToNextLeft = true;
//			}
//		}while(goToNextLeft);
//		return !alldone;
//		}
//
//
//	@Override
//	public Text getCurrentKey() throws IOException, InterruptedException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//	@Override
//	public Text getCurrentValue() throws IOException, InterruptedException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//	@Override
//	public float getProgress() throws IOException, InterruptedException {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//
//	@Override
//	public void close() throws IOException {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
