package com.hadoop.mapReduce1;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;


public class MaxTemperature {
	public static void main(String args[]) throws IOException{
//		if(args.length!=2) {
//			System.err.println("Usage:MaxTemperature<input path><output path>");
//			System.exit(-1);
//		}
		System.out.println("begin to check the String args[]");
		
		for(int i = 0; i<args.length;i++) {
			System.out.println("===开始获取传入的参数=====");
			System.out.println(args[0]);
		}
		
		String inputPath = "/user/test_idex6/input/029070-99999-1901";
		String outPath="/user/test_idex6/output/pre/result.txt";
				
		
		//
		JobConf conf = new JobConf(MaxTemperature.class);
		conf.setJobName("Max temperature");
		
		
		//设置输入和输入目录
//		FileInputFormat.addInputPath(conf, new Path(args[0]));
//		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		System.out.println("set the input and output path");
		System.out.println("the input path  "+inputPath);
		System.out.println("the out path  "+outPath);
		FileInputFormat.addInputPath(conf, new Path(inputPath));
		FileOutputFormat.setOutputPath(conf, new Path(outPath));
		
		
		//设置map ,reduce处理类
		conf.setMapperClass(MaxTemperatureMapper.class);
		conf.setReducerClass(MaxTemperatureReducer.class);
		
		
		//设置输入类型
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		System.out.println("begin to start task");
		JobClient.runJob(conf);
		
	}

}
