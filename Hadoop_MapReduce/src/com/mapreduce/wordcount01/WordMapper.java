package com.mapreduce.wordcount01;
import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordMapper extends Mapper<Object,Text,Text,IntWritable>{
	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	public void map(Object key,Text value,Context context)
	       throws IOException,InterruptedException{
		StringTokenizer itr = new StringTokenizer(value.toString());//将一行文本分解多个字符串
		while(itr.hasMoreTokens()) {
			word.set(itr.nextToken());//设置文本内容
			context.write(word, one);//设置文本个数，每个文本记录一个
		}	
	}
}
