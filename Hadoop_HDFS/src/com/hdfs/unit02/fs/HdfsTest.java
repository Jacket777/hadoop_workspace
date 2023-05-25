package com.hdfs.unit02.fs;

import java.net.URI;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

public class HdfsTest {
	private FileSystem fs = null;
	private List<String>hdfsPathList;
	
	@Before
	public void init()throws Exception{
		Configuration conf = new Configuration();
		fs = FileSystem.get(new URI("hdfs://192.168.85.20"),conf,"root");	
	}
	
	
	@Test
	public void testMkdir()throws Exception{
		boolean flag = fs.mkdirs(new Path("/javaApi/mk/dir1/dir2"));
		System.out.println(flag?"创建成功":"创建失败");
	}
	
}
