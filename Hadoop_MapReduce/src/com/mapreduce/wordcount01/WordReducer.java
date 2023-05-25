package com.mapreduce.wordcount01;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordReducer extends Reducer<Text,IntWritable,Text,IntWritable>{
	private IntWritable result = new IntWritable();
	public void reduce(Text key,Iterable<IntWritable>values,Context context) 
	            throws IOException,InterruptedException{
		int sum = 0;
		for(IntWritable val:values) {
			sum +=val.get();//取出值，并进行累加计算
		}
		result.set(sum);
		context.write(key,result);		
	}
}