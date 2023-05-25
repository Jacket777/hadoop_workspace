package com.mapreduce.wordcount03;
import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text,LongWritable,Text,LongWritable>{
	@Override
	protected void reduce(Text key,Iterable<LongWritable>values,Context context) 
	    throws IOException, InterruptedException{
		Iterator<LongWritable>it = values.iterator();
		long sum =0;
		while(it.hasNext()) {
			sum +=it.next().get();
		}
		context.write(key, new LongWritable(sum));	
	}
}