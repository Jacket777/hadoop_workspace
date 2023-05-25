package com.hbase.demo02;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

/*
 * 实例程序：从HBase读数据到HDFS
 * 1.创建一张新表
 */

public class CreateNewTable {
	public static void main(String[] args) throws IOException{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.150.130:2181");
		
		HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf("phoneurl"));
		HColumnDescriptor columnDesc = new HColumnDescriptor("baseinfo");
		columnDesc.setMaxVersions(5);
		tableDesc.addFamily(columnDesc);
		
		HBaseAdmin hbaseAdmin = new HBaseAdmin(conf);
		hbaseAdmin.createTable(tableDesc);
		hbaseAdmin.close();
	}
}
