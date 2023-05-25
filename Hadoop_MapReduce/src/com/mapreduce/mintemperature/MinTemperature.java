package com.mapreduce.mintemperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.fs.Path;


public class MinTemperature {
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(MinTemperature.class);
		
		job.setMapperClass(MinTemperatureMapper.class);
		job.setReducerClass(MinTemperatureReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path("file:///simple/source.txt"));
		FileOutputFormat.setOutputPath(job, new Path("file:///simple/out"));
		
		System.exit(job.waitForCompletion(true)?0:1);
	}

}
