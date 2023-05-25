package com.hbase.demo02;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * ʵ�����򣺴�HBase�����ݵ�HDFS
 * 4.Reducer����map������
 * ��map�����һ�����ݼ���д��ȥ
 */

public class HdfsSinkReducer extends Reducer<Text,NullWritable,Text,NullWritable>{
	@Override
	protected void reduce(Text key,Iterable<NullWritable>values,Context context)
	throws IOException,InterruptedException{
		context.write(key, NullWritable.get());
	}
}
