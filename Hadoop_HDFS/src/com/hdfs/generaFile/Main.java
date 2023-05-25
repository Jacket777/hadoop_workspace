package com.hdfs.generaFile;
import org.apache.hadoop.conf.Configuration;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException{
		generateHdfsFile(args[0]);}
	
	public static void generateHdfsFile(String path)throws IOException{
		for(int threadNum = 0 ; threadNum < 100; threadNum++) {
			String threadPath =  path + "/"+threadNum;
			Configuration conf = new Configuration();
			new HdfsGenerateFile(threadPath, conf).start();
		}
	}
}