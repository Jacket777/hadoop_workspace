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
 * 专用过滤器PageFilter的使用
 */


public class PageFilterTest {
	public void testFilter()throws IOException{
		
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.150.130:2181");
		
		HTable table = new HTable(conf,"account3");
		//1.创建过滤器PageFilter，该过滤器表示按行分页，参数3表示每个分页有3行记录
		Filter filter = new PageFilter(3);
		final byte[]POSTFIX = new byte[] {0x00};  //POSTFIX=0;
		int totalRows = 0;
		byte[]lastRow = null;
		//2.进入循环，遍历所有
		while(true) {
			//3.初始化scan实例，该实例用于查询符合条件的数据
			Scan scan = new Scan();
			//4.设置过滤器，将创建好的分页过滤器设置到scan实例中
			scan.setFilter(filter);
			//5.设置遍历的开始位置，表示开始的行键位置，如果是第一次循环（就是第一页）则不进入该语句块
			if(lastRow!=null) {
			
				byte[]startRow = Bytes.add(lastRow, POSTFIX);//注意这里添加POSTFIX操作，如果不加则死循环，实质是加0
				System.out.println("start row: "+Bytes.toStringBinary(startRow));
				scan.setStartRow(startRow);	
			}
			//6.执行查询，使用HTable实例进行扫描查询，并且将扫描结果输出，并且给行键遍历赋值
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
			//7.关闭ResultScanner实例
			scanner.close();
			//8.跳出循环条件
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
