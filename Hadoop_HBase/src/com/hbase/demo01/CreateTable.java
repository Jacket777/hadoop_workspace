package com.hbase.demo01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;



/*
 * HBase创建表
 */
public class CreateTable {
	public static void main(String[] args) throws IOException{
		//1.配置文件设置
		Configuration conf = HBaseConfiguration.create();//创建用于客户端的配置类实例
		conf.set("hbase.zookeeper.quorum", "192.168.150.130:2181");//设置连接zk地址，hbase客户端连接的是zookeeper
		
		//2.表描述相关信息
		//创建表描述器，并命名表名为account1
		HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf("account1"));
		//创建列族描述器，并命名一个列族名为baseinfo
		HColumnDescriptor columnDesc1 = new HColumnDescriptor("baseinfo");
		columnDesc1.setMaxVersions(5);//设置列族的最大版本数
		//创建列族描述器，并命名一个列族名为contacts
		HColumnDescriptor columnDesc2 = new HColumnDescriptor("contacts");
		columnDesc2.setMaxVersions(3);//设置列族的最大版本数
		tableDesc.addFamily(columnDesc1);//添加一个列族给表
		tableDesc.addFamily(columnDesc2);//添加一个列族给表
		
		//3.实例化HBaseAdmin,创建表
		HBaseAdmin hbaseAdmin = new HBaseAdmin(conf);//根据配置文件创建HBaseAdmin对象
		hbaseAdmin.createTable(tableDesc);//创建表
		
		//4.释放资源
		hbaseAdmin.close();
	}
}
