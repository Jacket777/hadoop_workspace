package com.hbase.demo01;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;


/*
 * 删除表实例
 */

public class DelTable {
	public static void main(String[] args) throws IOException{
		//1.配置文件设置
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.150.130:2181");
		
		//2.根据配置文件创建HBaseAdmin对象
		HBaseAdmin hbaseAdmin = new HBaseAdmin(conf);
		
		//3.删除表
		hbaseAdmin.disableTable("account1");//必须先设置表不可用
		hbaseAdmin.deleteTable("account1");//删表
		
		//4.释放资源
		hbaseAdmin.close();
	}
}
