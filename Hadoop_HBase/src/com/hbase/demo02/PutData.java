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
 * 实例程序：从HBase读数据到HDFS
 * 2.向表中插入测试数据
 */


public class PutData {
	public static void main(String[] args) throws IOException{
		//1.配置文件设置
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.150.130");
		
		//2.获得要操作的表的对象
		HTable table = new HTable(conf,"phoneurl");
		
		//3.设置Put对象
		//设置行键值，设置列族，列，cell值
		Put put1 = new Put(Bytes.toBytes("1591235350"));
		put1.add(Bytes.toBytes("baseinfo"), Bytes.toBytes("url"), Bytes.toBytes("www.test.com"));
		
		Put put2 = new Put(Bytes.toBytes("1591235351"));
		put2.add(Bytes.toBytes("baseinfo"), Bytes.toBytes("url"), Bytes.toBytes("www.test2.com"));
		
		Put put3 = new Put(Bytes.toBytes("1591235352"));
		put3.add(Bytes.toBytes("baseinfo"),Bytes.toBytes("url"),Bytes.toBytes("www.test3.com"));
		
		//4.构造List<Put>
		List<Put>listPut = new ArrayList<Put>();
		listPut.add(put1);
		listPut.add(put2);
		listPut.add(put3);
		
		//5.插入多行数据
		table.put(listPut);
		
		//6.释放资源
		table.close();
	}
}
