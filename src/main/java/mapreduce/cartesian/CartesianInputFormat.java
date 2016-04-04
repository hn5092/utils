//package com.x.hadoop.mr.cartesian;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapred.JobConf;
//import org.apache.hadoop.mapred.Reporter;
//import org.apache.hadoop.mapreduce.InputSplit;
//import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.RecordReader;
//import org.apache.hadoop.mapreduce.TaskAttemptContext;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.join.CompositeInputSplit;
//import org.apache.hadoop.util.ReflectionUtils;
//
//public class   CartesianInputFormat   extends FileInputFormat<Text, Text> {
//	public static final String LEFT_INPUT_FORMAT = "cart.left.inputformat";
//	public static final String LEFT_INPUT_PATH = "cart.left.path";
//
//	public static final String RIGHT_INPUT_FORMAT = "cart.right.inputformat";
//
//	public static final String RIGHT_INPUT_PATH = "cart.right.path";
//
//	public static void setLeftInputInfo (JobConf job , Class <? extends FileInputFormat> inputFormat, String inputPath){
//		job.set(LEFT_INPUT_FORMAT, inputFormat.getCanonicalName());
//		job.set(LEFT_INPUT_PATH, inputPath);
////		
//	}
//	public static void setRightInputInfo (JobConf job , Class <? extends FileInputFormat> inputFormat, String inputPath){
//		job.set(RIGHT_INPUT_FORMAT, inputFormat.getCanonicalName());
//		job.set(RIGHT_INPUT_PATH, inputPath);
//	}
//	public InputSplit[] getSplits(Job job, int numSplits)
//			throws IOException{
//		//get the input split from both the left and right data sets
//		List<InputSplit> leftSplits = null;
//		try {
//			leftSplits = getInputSplits(job, job.getConfiguration().get(LEFT_INPUT_FORMAT),job.getConfiguration().get(LEFT_INPUT_PATH), numSplits);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		List<InputSplit> rightSplits = null;
//		try {
//			rightSplits = getInputSplits(job, job.getConfiguration().get(RIGHT_INPUT_FORMAT),job.getConfiguration().get(RIGHT_INPUT_PATH), numSplits);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//创建一个composite inputsplit
//		CompositeInputSplit[] coSplits = new CompositeInputSplit[leftSplits.size()*rightSplits.size()];
//		int i = 0;
//		for(InputSplit l: leftSplits){
//			for(InputSplit r: rightSplits){
//				coSplits[i] = new CompositeInputSplit(2);
//				try {
//					coSplits[i].add(l);
//					coSplits[i].add(r);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				i++;
//			}
//		}
//		return coSplits;
//		
//	}
//	private List<InputSplit> getInputSplits(Job job, String inputFormatClass,
//			String inputPath, int numSplits) throws IOException, ClassNotFoundException {
//		FileInputFormat inputFormat = (FileInputFormat)ReflectionUtils.newInstance(Class.forName(inputFormatClass), job.getConfiguration());
//		inputFormat.setInputPaths(job, inputPath);;
//		
//		List<InputSplit> splits = inputFormat.getSplits(job);
//		return splits;
//	}
//	@Override
//	public RecordReader<Text, Text> createRecordReader(
//			InputSplit split,
//			TaskAttemptContext context) throws IOException,
//			InterruptedException {
//		return new CartesianRecordReader((CompositeInputSplit) split, context);
//	}
//	
//
//}
