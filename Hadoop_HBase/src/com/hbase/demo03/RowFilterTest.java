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
 * 比较过滤器RowFilter使用
 */

public class RowFilterTest {
	public void testRowFilter()throws IOException{
		//1.配置文件设置
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.150.130:2181");
		
		//2.获得要操作表的对象
		HTable table = new HTable(conf,"account2");
		
		//3.创建scan对象
		Scan scan = new Scan();
		
		//4.1.查询rowkey小于等于rk08的行
		System.out.println("rowkey小于等于rk08的行");
		//设置过滤器，第一个参数为CompareOp，表示小于或等于，第二个参数为行键值
		Filter filter1 = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL,
				new BinaryComparator("rk08".getBytes()));
		//将过滤器设置给scan对象
		scan.setFilter(filter1);
		//获得查询结果
		ResultScanner scanner1 = table.getScanner(scan);
		//遍历结果
		for(Result res:scanner1) {
			System.out.println(res+"===>"+Bytes.toString(res.getValue(
					Bytes.toBytes("baseinfo"), Bytes.toBytes("name")))
			+"===>"+Bytes.toString(res.getValue(
					Bytes.toBytes("contacts"), Bytes.toBytes("address"))));	
		}
		//释放ResultScanner资源
		scanner1.close();
		
		//4.2.查询正则获取rowkey结尾为2的行
		System.out.println("正则获取rowkey结尾为2的行");
		//设置过滤器，第一个参数为CompareOp，表示等于，第二个参数为正则表达式，表示结尾含有2
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
		
		//4.3.查询rowkey包含有1的行
		System.out.println("rowkey包含有1的行");
		//设置过滤器，第一个参数为CompareOp，表示等于，第二个参数SubstringComparator，表示字符串中含有1
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
		
		//5.释放HTable资源
		table.close();	
	}
	
	
	public static void main(String[] args) throws IOException{
		RowFilterTest test = new RowFilterTest();
		test.testRowFilter();
	}
}
