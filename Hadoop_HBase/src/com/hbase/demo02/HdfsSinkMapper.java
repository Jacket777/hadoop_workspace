package com.hbase.demo02;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.NullWritable;

/*
 * 
 * 实例程序：从HBase读数据到HDFS
 * 3.map处理表中数据
 * TableMapper继承自Mapper类
 * 
 */
public class HdfsSinkMapper extends TableMapper<Text, NullWritable>{
	/*
	 * map拿到一行的内容
	 * 参数key代表hbase行键
	 * 参数Result代表一行字段的值
	 * 
	 */
	@Override
	protected void map(ImmutableBytesWritable key,Result value,Context context)
			throws IOException,InterruptedException{
		byte[]bytes = key.copyBytes();//hbase的rowkey,获取key的内容
		String phone = new String(bytes);//将key转换为String
		//value为url字段，属于行键baseinfo
		//根据列族名和列名获取列值
		byte[]urlbytes = value.getValue("baseinfo".getBytes(), "url".getBytes());
		String url = new String(urlbytes);//将列值转换为String
		context.write(new Text(phone+"\t"+url), NullWritable.get());//将一行数据写出去	
	}
}