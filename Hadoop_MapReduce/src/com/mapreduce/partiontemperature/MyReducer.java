package com.mapreduce.partiontemperature;

import java.io.IOException;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;

public class MyReducer extends Reducer<MyKey,Text,NullWritable,Text>{
	@Override
	protected void reduce(MyKey mykey,Iterable<Text>iterable,Context context) 
	     throws IOException,InterruptedException{
		int sum = 0;
		for(Text t: iterable) {
			sum++;
			if(sum>3) {
				break;
			}else {
				context.write(NullWritable.get(), t);
			}	
		}	
	}
}