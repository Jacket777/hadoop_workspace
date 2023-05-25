package com.simple.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsDAO {
	private static String hdfsPath ="hdfs://192.168.65.153:9000/usr/simple/";
	Configuration conf = new Configuration();
	
	/*
	 * 上传文件到HDFS上去
	 */
	public void copyFile(String local) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		fs.copyFromLocalFile(new Path(local), new Path(hdfsPath));
		fs.close();
	}
	
	/*
	 * 从HDFS上下载数据
	 */
	public void download(String remote,String local)throws IOException{
		
	}
	
	/*
	 * 从HDFS上删除文件
	 */
	public void deleteFromHdfs(String deletePath)throws FileNotFoundException,IOException{
		
	}
	
	/*
	 * 遍历HDFS上的文件和目录
	 */
	public FileStatus[]getDirectoryFromHdfs()throws FileNotFoundException,IOException{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		FileStatus[]list = fs.listStatus(new Path(hdfsPath));
		if(list!=null) {
			for(FileStatus f: list) {
				System.out.printf("name: %s, folder:%s, size:%d\n", 
						f.getPath().getName(),f.isDirectory(),f.getLen());
			}	
		}
		fs.close();
		return list;	
	}
}
