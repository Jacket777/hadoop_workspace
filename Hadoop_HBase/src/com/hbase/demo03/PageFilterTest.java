package com.hbase.demo03;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.ResultScanner;

/*
 * ר�ù�����PageFilter��ʹ��
 */


public class PageFilterTest {
	public void testFilter()throws IOException{
		
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.150.130:2181");
		
		HTable table = new HTable(conf,"account3");
		//1.����������PageFilter���ù�������ʾ���з�ҳ������3��ʾÿ����ҳ��3�м�¼
		Filter filter = new PageFilter(3);
		final byte[]POSTFIX = new byte[] {0x00};  //POSTFIX=0;
		int totalRows = 0;
		byte[]lastRow = null;
		//2.����ѭ������������
		while(true) {
			//3.��ʼ��scanʵ������ʵ�����ڲ�ѯ��������������
			Scan scan = new Scan();
			//4.���ù��������������õķ�ҳ���������õ�scanʵ����
			scan.setFilter(filter);
			//5.���ñ����Ŀ�ʼλ�ã���ʾ��ʼ���м�λ�ã�����ǵ�һ��ѭ�������ǵ�һҳ���򲻽��������
			if(lastRow!=null) {
			
				byte[]startRow = Bytes.add(lastRow, POSTFIX);//ע���������POSTFIX�����������������ѭ����ʵ���Ǽ�0
				System.out.println("start row: "+Bytes.toStringBinary(startRow));
				scan.setStartRow(startRow);	
			}
			//6.ִ�в�ѯ��ʹ��HTableʵ������ɨ���ѯ�����ҽ�ɨ������������Ҹ��м�������ֵ
			ResultScanner scanner = table.getScanner(scan);
			int localRows  = 0;
			Result result=null;
			while((result=scanner.next())!=null) {
				System.out.println(result+"===>"+Bytes.toString(result.getValue(
						Bytes.toBytes("baseinfo"), Bytes.toBytes("name")))
				+"===>"+Bytes.toString(result.getValue(
						Bytes.toBytes("contacts"), Bytes.toBytes("address"))));	
				totalRows++;
				localRows++;
				lastRow = result.getRow();	
			}
			System.out.println("");
			//7.�ر�ResultScannerʵ��
			scanner.close();
			//8.����ѭ������
			if(localRows ==0)break;
			
		}
		System.out.println("total rows: "+totalRows);
		table.close();	
	}
	
	public static void main(String[] args) throws IOException{
		PageFilterTest test = new PageFilterTest();
		test.testFilter();
	}
}
