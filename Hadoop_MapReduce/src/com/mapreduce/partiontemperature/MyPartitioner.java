package com.mapreduce.partiontemperature;

import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.io.Text;

public class MyPartitioner extends Partitioner<MyKey, Text>{
	@Override
	public int getPartition(MyKey key, Text value, int numPartions) {
		return (key.getYear()-1949)%numPartions;
	}
}
