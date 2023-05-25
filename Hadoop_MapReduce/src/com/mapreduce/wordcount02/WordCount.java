package com.mapreduce.wordcount02;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;


public class WordCount {
	public static void main(String[] args) 
			throws IOException,ClassNotFoundException,InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(WordCount.class);
		job.setMapperClass(WcMapper.class);
		job.setReducerClass(WcReducer.class);
		FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.0.2:9000/words.txt"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.0.2:9000/out"));
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		job.waitForCompletion(true);
	}
}