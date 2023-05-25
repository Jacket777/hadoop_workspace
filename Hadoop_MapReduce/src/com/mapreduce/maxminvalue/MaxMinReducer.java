package com.mapreduce.maxminvalue;
import org.apache.hadoop.io.Text;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class MaxMinReducer extends Reducer<Text, LongWritable,Text,Text> {
	private long max = Long.MIN_VALUE;
	private long min = Long.MAX_VALUE;
	
	@Override
	protected void reduce(Text key, Iterable<LongWritable>values,Context context)
	     throws IOException,InterruptedException{
		for(LongWritable val:values) {
			long temp = val.get();
			if(temp>max) {
				max=temp;
			}
			if(temp<min) {
				min=temp;
			}	
		}
		context.write(new Text("max"), new Text(max+""));
		context.write(new Text("Min"), new Text(min+""));	
	}
}
