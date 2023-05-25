package com.hbase.demo02;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

/*
 * ʵ�����򣺴�HBase�����ݵ�HDFS
 * 2.����в����������
 */


public class PutData {
	public static void main(String[] args) throws IOException{
		//1.�����ļ�����
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.150.130");
		
		//2.���Ҫ�����ı�Ķ���
		HTable table = new HTable(conf,"phoneurl");
		
		//3.����Put����
		//�����м�ֵ���������壬�У�cellֵ
		Put put1 = new Put(Bytes.toBytes("1591235350"));
		put1.add(Bytes.toBytes("baseinfo"), Bytes.toBytes("url"), Bytes.toBytes("www.test.com"));
		
		Put put2 = new Put(Bytes.toBytes("1591235351"));
		put2.add(Bytes.toBytes("baseinfo"), Bytes.toBytes("url"), Bytes.toBytes("www.test2.com"));
		
		Put put3 = new Put(Bytes.toBytes("1591235352"));
		put3.add(Bytes.toBytes("baseinfo"),Bytes.toBytes("url"),Bytes.toBytes("www.test3.com"));
		
		//4.����List<Put>
		List<Put>listPut = new ArrayList<Put>();
		listPut.add(put1);
		listPut.add(put2);
		listPut.add(put3);
		
		//5.�����������
		table.put(listPut);
		
		//6.�ͷ���Դ
		table.close();
	}
}
