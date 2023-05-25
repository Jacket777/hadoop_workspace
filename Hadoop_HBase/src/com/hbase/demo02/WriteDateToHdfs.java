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
 * 实例程序：从HBase读数据到HDFS
 * 5.主程序
 */

public class WriteDateToHdfs {
	public static String tableName = "phoneurl";
	
	public static void main(String[] args) throws Exception{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.150.130:2181");
		
		Job job = Job.getInstance();
		job.setJarByClass(WriteDateToHdfs.class);
		Scan scan = new Scan();
		
		//设置mapper
		//1.第一个参数为表名
		//2.第二个参数为scan,要写表中所有的数据，所以可以指定起始的行键范围
		//3.第三个参数为Mapper类,第四个参数为输出的keyclass,第五个参数为输出的valueclass
		TableMapReduceUtil.initTableMapperJob(tableName,scan,HdfsSinkMapper.class,
				Text.class,NullWritable.class,job);
		job.setReducerClass(HdfsSinkReducer.class);
		
		FileOutputFormat.setOutputPath(job, new Path("d:/hbasetest/output"));
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		job.waitForCompletion(true);
	}
}
