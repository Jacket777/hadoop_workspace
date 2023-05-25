package com.hbase.demo01;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;


/*
 * ɾ����ʵ��
 */

public class DelTable {
	public static void main(String[] args) throws IOException{
		//1.�����ļ�����
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.150.130:2181");
		
		//2.���������ļ�����HBaseAdmin����
		HBaseAdmin hbaseAdmin = new HBaseAdmin(conf);
		
		//3.ɾ����
		hbaseAdmin.disableTable("account1");//���������ñ�����
		hbaseAdmin.deleteTable("account1");//ɾ��
		
		//4.�ͷ���Դ
		hbaseAdmin.close();
	}
}
