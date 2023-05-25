package com.hbase.demo02;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.NullWritable;

/*
 * 
 * ʵ�����򣺴�HBase�����ݵ�HDFS
 * 3.map�����������
 * TableMapper�̳���Mapper��
 * 
 */
public class HdfsSinkMapper extends TableMapper<Text, NullWritable>{
	/*
	 * map�õ�һ�е�����
	 * ����key����hbase�м�
	 * ����Result����һ���ֶε�ֵ
	 * 
	 */
	@Override
	protected void map(ImmutableBytesWritable key,Result value,Context context)
			throws IOException,InterruptedException{
		byte[]bytes = key.copyBytes();//hbase��rowkey,��ȡkey������
		String phone = new String(bytes);//��keyת��ΪString
		//valueΪurl�ֶΣ������м�baseinfo
		//������������������ȡ��ֵ
		byte[]urlbytes = value.getValue("baseinfo".getBytes(), "url".getBytes());
		String url = new String(urlbytes);//����ֵת��ΪString
		context.write(new Text(phone+"\t"+url), NullWritable.get());//��һ������д��ȥ	
	}
}