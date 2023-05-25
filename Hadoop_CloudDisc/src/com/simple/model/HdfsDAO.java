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
	 * �ϴ��ļ���HDFS��ȥ
	 */
	public void copyFile(String local) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		fs.copyFromLocalFile(new Path(local), new Path(hdfsPath));
		fs.close();
	}
	
	/*
	 * ��HDFS����������
	 */
	public void download(String remote,String local)throws IOException{
		
	}
	
	/*
	 * ��HDFS��ɾ���ļ�
	 */
	public void deleteFromHdfs(String deletePath)throws FileNotFoundException,IOException{
		
	}
	
	/*
	 * ����HDFS�ϵ��ļ���Ŀ¼
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
