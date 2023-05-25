package com.hbase.demo02;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
 * ʵ�����򣺴�HBase�����ݵ�HDFS
 * 5.������
 */

public class WriteDateToHdfs {
	public static String tableName = "phoneurl";
	
	public static void main(String[] args) throws Exception{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.150.130:2181");
		
		Job job = Job.getInstance();
		job.setJarByClass(WriteDateToHdfs.class);
		Scan scan = new Scan();
		
		//����mapper
		//1.��һ������Ϊ����
		//2.�ڶ�������Ϊscan,Ҫд�������е����ݣ����Կ���ָ����ʼ���м���Χ
		//3.����������ΪMapper��,���ĸ�����Ϊ�����keyclass,���������Ϊ�����valueclass
		TableMapReduceUtil.initTableMapperJob(tableName,scan,HdfsSinkMapper.class,
				Text.class,NullWritable.class,job);
		job.setReducerClass(HdfsSinkReducer.class);
		
		FileOutputFormat.setOutputPath(job, new Path("d:/hbasetest/output"));
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		job.waitForCompletion(true);
	}
}
