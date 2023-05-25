package com.mapreduce.wordcount03;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;


public class WordCountMapper extends Mapper<LongWritable,Text,Text,LongWritable>{
	@Override
	protected void map(LongWritable key,Text value,Context context)
			throws IOException,InterruptedException{
		String valueString = value.toString();
		String arry[] = valueString.split("");
		for(int i = 0; i<arry.length;i++) {
			context.write(new Text(arry[i]), new LongWritable(1));
		}	
	}
}