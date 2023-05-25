package com.hdfs.generaFile;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class HdfsGenerateFile extends Thread{
	private String operationPath;
	private FileSystem fs;
	
	private static final Logger LOG = LoggerFactory.getLogger("aaaaa");
	
	public HdfsGenerateFile(String path,Configuration conf) throws IOException{
		operationPath = path;
		fs = new Path(path).getFileSystem(conf);
	}
	
	public void generateFile()throws IOException{
		for(int i = 0;i<10000;i++) {
			FSDataOutputStream outputStream = fs.create(new Path(operationPath));
			outputStream.write("hello world".getBytes());
			outputStream.close();
		}
	}
	
	public void run() {
		try {
			generateFile();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
