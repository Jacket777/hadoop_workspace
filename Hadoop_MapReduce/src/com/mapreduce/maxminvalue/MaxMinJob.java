package com.mapreduce.maxminvalue;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.fs.Path;

public class MaxMinJob {

	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(MaxMinJob.class);
		job.setMapperClass(MaxMinMapper.class);
		job.setReducerClass(MaxMinReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job, new Path("file:///simple/source.txt"));
		FileOutputFormat.setOutputPath(job,new Path("file:///simple/out"));
		System.exit(job.waitForCompletion(true)?0:1);
	}
}
