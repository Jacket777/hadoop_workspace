package com.mapreduce.wordcount02;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WcMapper extends Mapper<LongWritable,Text,Text,LongWritable>{
	@Override
	protected void map(LongWritable key,Text value,Context context) 
	     throws IOException,InterruptedException{
		String line = value.toString();//接收数据
		String[]words = line.split("");//切分数据
		for(String word: words) {
			//出现1次，记一次输出
			context.write(new Text(word), new LongWritable(1));
		}	
	}
}
