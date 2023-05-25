package com.hadoop.mapReduce1;

import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;


public class MaxTemperatureReducer extends MapReduceBase
  implements Reducer<Text,IntWritable,Text,IntWritable>{

	@Override
	public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {
		System.out.println("the reduce task begin to start");
		int maxValue = Integer.MIN_VALUE;
		while(values.hasNext()) {
			maxValue = Math.max(maxValue, values.next().get());
		}
		output.collect(key, new IntWritable(maxValue));
	}
}
