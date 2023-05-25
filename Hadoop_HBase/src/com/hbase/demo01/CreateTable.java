package com.hbase.demo01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;



/*
 * HBase������
 */
public class CreateTable {
	public static void main(String[] args) throws IOException{
		//1.�����ļ�����
		Configuration conf = HBaseConfiguration.create();//�������ڿͻ��˵�������ʵ��
		conf.set("hbase.zookeeper.quorum", "192.168.150.130:2181");//��������zk��ַ��hbase�ͻ������ӵ���zookeeper
		
		//2.�����������Ϣ
		//������������������������Ϊaccount1
		HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf("account1"));
		//����������������������һ��������Ϊbaseinfo
		HColumnDescriptor columnDesc1 = new HColumnDescriptor("baseinfo");
		columnDesc1.setMaxVersions(5);//������������汾��
		//����������������������һ��������Ϊcontacts
		HColumnDescriptor columnDesc2 = new HColumnDescriptor("contacts");
		columnDesc2.setMaxVersions(3);//������������汾��
		tableDesc.addFamily(columnDesc1);//���һ���������
		tableDesc.addFamily(columnDesc2);//���һ���������
		
		//3.ʵ����HBaseAdmin,������
		HBaseAdmin hbaseAdmin = new HBaseAdmin(conf);//���������ļ�����HBaseAdmin����
		hbaseAdmin.createTable(tableDesc);//������
		
		//4.�ͷ���Դ
		hbaseAdmin.close();
	}
}
