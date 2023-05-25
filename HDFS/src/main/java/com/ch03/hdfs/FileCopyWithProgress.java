package com.ch03.hdfs;

import java.net.URI;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.util.Progressable;
import org.apache.hadoop.conf.Configuration;

//example -4 copy local file to hdfs  :PASS
public class FileCopyWithProgress {
	public static void main(String[] args) throws Exception{
		String localSrc = args[0];//the local file 
		String dst = args[1];//the distance file
		InputStream in = new BufferedInputStream(
				new FileInputStream(localSrc));
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		OutputStream out = fs.create(new Path(dst), new Progressable() {
			@Override
			public void progress() {
				System.out.println("====>>>======");	
			}
		});
		IOUtils.copyBytes(in, out, 4096, true);	
	}
}