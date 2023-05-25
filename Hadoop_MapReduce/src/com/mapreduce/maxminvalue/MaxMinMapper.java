package com.mapreduce.maxminvalue;
import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxMinMapper extends Mapper<LongWritable,Text,Text,LongWritable>{
	private final Text keyText = new Text("k");//为map设置一个固定的输出键值
	private LongWritable val = new LongWritable(0);//初始化value值
	
	@Override
	protected void map(LongWritable key,Text value,Context context)
	throws IOException,InterruptedException{
		val.set(Long.parseLong(value.toString()));//设置value值
		context.write(keyText, val);
	}
}