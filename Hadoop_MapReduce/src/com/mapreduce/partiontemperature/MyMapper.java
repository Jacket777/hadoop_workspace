package com.mapreduce.partiontemperature;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


//1949 12 31 12:32:19 39
public class MyMapper extends Mapper<Text,Text,MyKey,Text>{
	@Override
	protected void map(Text key,Text value,Context context)
	 throws IOException,InterruptedException{
		String[]strArray = key.toString().split("-");
		MyKey myKey = new MyKey();
		myKey.setYear(Integer.parseInt(strArray[0]));
		myKey.setMonth(Integer.parseInt(strArray[1]));
		myKey.setAir(Double.parseDouble(value.toString()));	
		context.write(myKey, new Text(key.toString()+"\t"+value));	
	}
}