package com.mapreduce.partiontemperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;

public class Weather {
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass( Weather.class);
		
		job.setMapperClass(MyMapper.class);
		//job.setInputFormatClass(KeyValueTextInputFormat.class);
		job.setReducerClass(MyReducer.class);
		job.setPartitionerClass(MyPartitioner.class);
		job.setSortComparatorClass(MySort.class);
		job.setGroupingComparatorClass(MyGroup.class);
		job.setNumReduceTasks(3);
		
		job.setOutputKeyClass(MyKey.class);
		job.setMapOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path("/simple/source.txt"));
		FileOutputFormat.setOutputPath(job, new Path("/simple/out"));
		System.exit(job.waitForCompletion(true)?0:1);
	}
}
