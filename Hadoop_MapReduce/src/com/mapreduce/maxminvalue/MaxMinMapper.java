package com.mapreduce.maxminvalue;
import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxMinMapper extends Mapper<LongWritable,Text,Text,LongWritable>{
	private final Text keyText = new Text("k");//Ϊmap����һ���̶��������ֵ
	private LongWritable val = new LongWritable(0);//��ʼ��valueֵ
	
	@Override
	protected void map(LongWritable key,Text value,Context context)
	throws IOException,InterruptedException{
		val.set(Long.parseLong(value.toString()));//����valueֵ
		context.write(keyText, val);
	}
}