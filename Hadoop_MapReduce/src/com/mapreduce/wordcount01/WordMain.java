package com.mapreduce.wordcount01;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;



public class WordMain {
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		String[]otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
		/*
		 * 设置输入，输出
		 */
		if(otherArgs.length!=2) {
			System.out.println("Usage: wordcount<in><out>");
			System.exit(2);
		}
		@SuppressWarnings("deprecation")
		Job job = new Job(conf,"word count");
		job.setJarByClass(WordMain.class);//主类
		job.setMapperClass(WordMapper.class);//设置Mapper
		job.setCombinerClass(WordReducer.class);//作业合成类
		job.setReducerClass(WordReducer.class);//设置Reducer
		job.setOutputKeyClass(Text.class);//设置作业输出数据的关键类
		job.setOutputValueClass(IntWritable.class);//设置作业输出值类
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));//文件输入
		FileOutputFormat.setOutputPath(job,new Path(otherArgs[1]));//文件输出
		System.exit(job.waitForCompletion(true)?0:1);//等待完成退出
	}
}
