package com.ch03.hdfs;

import org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


//example -5
public class ShowFileStatusTest {
	private MiniDFSCluster cluster;   //hadoop 1.0.4
	private FileSystem fs;
	
	@Before
	public void setUp()throws IOException{
		Configuration conf = new Configuration();
		 //conf.set("fs.default.name","hdfs://192.168.150.130:9000");
         //conf.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");
		if(System.getProperty("test.build.data")==null) {
			System.setProperty("test.build.data", "/tmp");
		}
		cluster = new MiniDFSCluster(conf, 1, true,null);
		//cluster = new MiniDFSCluster.Builder(conf).build();
		fs = cluster.getFileSystem();
		OutputStream  out = fs.create(new Path("/test10/testfile"));
		out.write("content".getBytes("UTF-8"));
		out.close();	
	}
	
	@After
	public void tearDown()throws IOException{
		if(fs!=null) {
			fs.close();
		}
		
		if(cluster!=null) {
			cluster.shutdown();
		}
	}
	
	
	@Test(expected=FileNotFoundException.class)
	public void throwsFileNotFoundForNonExistentFile()throws IOException{
		fs.getFileStatus(new Path("no-such-file"));
	}
	
	@Test
	public void fileStatusForFile()throws IOException{
		Path file = new Path("/test10/testfile");
		FileStatus stat = fs.getFileStatus(file);
		System.out.println("the path is=="+stat.getPath().toUri().getPath());
		//assertThat(stat.getPath().toUri().getPath(), Is<T>);
		System.out.println("is Dir=="+stat.isDir());
		System.out.println(" the len=="+stat.getLen());
		System.out.println("the modification time ==="+stat.getModificationTime());
		System.out.println("the replication is "+stat.getReplication());
		System.out.println("the blocksize is "+stat.getBlockSize());
		System.out.println("the owner is"+stat.getOwner());
		System.out.println("the group is"+stat.getGroup());	
	}
	
	
	
	@Test
	public void fileStatusForDirectory()throws IOException{
		Path file = new Path("/test10");
		FileStatus stat = fs.getFileStatus(file);
		System.out.println("the path is=="+stat.getPath().toUri().getPath());
		System.out.println("is Dir=="+stat.isDir());
		System.out.println(" the len=="+stat.getLen());
		System.out.println("the modification time ==="+stat.getModificationTime());
		System.out.println("the replication is "+stat.getReplication());
		System.out.println("the blocksize is "+stat.getBlockSize());
		System.out.println("the owner is"+stat.getOwner());
		System.out.println("the group is"+stat.getGroup());	
		System.out.println(stat.getPermission().toString());
		
	}
	
}