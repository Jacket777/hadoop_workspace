package com.hbase.demo03;

import java.io.IOException;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.Configuration;


/*
 * �ȽϹ�����RowFilterʹ��
 */

public class RowFilterTest {
	public void testRowFilter()throws IOException{
		//1.�����ļ�����
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.150.130:2181");
		
		//2.���Ҫ������Ķ���
		HTable table = new HTable(conf,"account2");
		
		//3.����scan����
		Scan scan = new Scan();
		
		//4.1.��ѯrowkeyС�ڵ���rk08����
		System.out.println("rowkeyС�ڵ���rk08����");
		//���ù���������һ������ΪCompareOp����ʾС�ڻ���ڣ��ڶ�������Ϊ�м�ֵ
		Filter filter1 = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL,
				new BinaryComparator("rk08".getBytes()));
		//�����������ø�scan����
		scan.setFilter(filter1);
		//��ò�ѯ���
		ResultScanner scanner1 = table.getScanner(scan);
		//�������
		for(Result res:scanner1) {
			System.out.println(res+"===>"+Bytes.toString(res.getValue(
					Bytes.toBytes("baseinfo"), Bytes.toBytes("name")))
			+"===>"+Bytes.toString(res.getValue(
					Bytes.toBytes("contacts"), Bytes.toBytes("address"))));	
		}
		//�ͷ�ResultScanner��Դ
		scanner1.close();
		
		//4.2.��ѯ�����ȡrowkey��βΪ2����
		System.out.println("�����ȡrowkey��βΪ2����");
		//���ù���������һ������ΪCompareOp����ʾ���ڣ��ڶ�������Ϊ������ʽ����ʾ��β����2
		Filter filter2 = new RowFilter(CompareFilter.CompareOp.EQUAL,
				new RegexStringComparator(".*2$"));
		scan.setFilter(filter2);
		ResultScanner scanner2 = table.getScanner(scan);
		for(Result res:scanner2) {
			System.out.println(res+"===>"+Bytes.toString(res.getValue(
					Bytes.toBytes("baseinfo"), Bytes.toBytes("name")))
			+"===>"+Bytes.toString(res.getValue(
					Bytes.toBytes("contacts"), Bytes.toBytes("address"))));	
		}
		scanner2.close();
		
		//4.3.��ѯrowkey������1����
		System.out.println("rowkey������1����");
		//���ù���������һ������ΪCompareOp����ʾ���ڣ��ڶ�������SubstringComparator����ʾ�ַ����к���1
		Filter filter3 = new RowFilter(CompareFilter.CompareOp.EQUAL,
				new SubstringComparator("1"));
		scan.setFilter(filter3);
		ResultScanner scanner3 = table.getScanner(scan);
		for(Result res:scanner3) {
			System.out.println(res+"===>"+Bytes.toString(res.getValue(
					Bytes.toBytes("baseinfo"), Bytes.toBytes("name")))
			+"===>"+Bytes.toString(res.getValue(
					Bytes.toBytes("contacts"), Bytes.toBytes("address"))));	
		}
		scanner3.close();
		
		//5.�ͷ�HTable��Դ
		table.close();	
	}
	
	
	public static void main(String[] args) throws IOException{
		RowFilterTest test = new RowFilterTest();
		test.testRowFilter();
	}
}
