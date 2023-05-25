package com.mapreduce.maxtemperature;

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

		System.out.println("begin to check the String args[]");
		System.out.println("the total parameter is "+args.length);
		for(int i = 0; i<args.length;i++) {
			System.out.println("=======>��ʼ��ȡ����Ĳ���");
			System.out.println("The "+i+"parameter is "+args[i]);
		
		}
//		
//		if(args.length!=2) {
//			System.err.println("Usage:MaxTemperature<input path><output path>");
//			System.exit(-1);
//		}
		

		JobConf conf = new JobConf(MaxTemperature.class);
		conf.setJobName("Max temperature");
		String outpath = args[3]+"/"+System.currentTimeMillis();
		System.out.println("���Ŀ¼Ϊ: "+outpath);
		
		
		//�������������Ŀ¼
		FileInputFormat.addInputPath(conf, new Path(args[2]));
		FileOutputFormat.setOutputPath(conf, new Path(outpath));

		
		
		//����map ,reduce������
		conf.setMapperClass(MaxTemperatureMapper.class);
		conf.setReducerClass(MaxTemperatureReducer.class);
		
		
		//������������
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		System.out.println("Begin to start task");
		JobClient.runJob(conf);	
	}
}