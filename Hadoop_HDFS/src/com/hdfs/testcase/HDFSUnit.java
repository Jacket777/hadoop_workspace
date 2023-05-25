package com.hdfs.testcase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.BlockLocation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.URI;

/*
 * HDFS 单元测试
 */
public class HDFSUnit {
	public static final String HDFS_PATH = "hdfs://hadoop0000:8020";
	Configuration conf = null;
	FileSystem fs = null;
	
	@Before
	public void setUp()throws Exception{
		System.out.println("HDFS APP SET UP");
		conf = new Configuration();
		fs = FileSystem.get(new URI(HDFS_PATH), conf);
	}
	
	@After
	public void tearDown()throws Exception{
		fs = null;
		conf = null;
		System.out.println("HDFS APP TEAR DOWN");
	}
	
	/*
	 * 1.创建目录
	 */
	@Test
	public void mkdir()throws Exception{
		fs.mkdirs(new Path("/hdfsapi/test"));
	}
	
	/*
	 * 2.创建文件
	 */
	@Test
	public void create()throws Exception{
		FSDataOutputStream output = fs.create(new Path("/hdfsapi/test/a.txt"));
		output.write("hell world".getBytes());
		output.flush();
		output.close();
	}
	
	/*
	 * 3.重命名
	 */
	@Test
	public void rename()throws Exception{
		Path oldPath = new Path("/hdfsapi/test/a.txt");
		Path newPath = new Path("/hdfsapi/test/b.txt");
		System.out.println(fs.rename(oldPath, newPath));
	}
	
	
	/*
	 * 4.上传本地文件到HDFS
	 */
	@Test
	public void copyFromLocalFile()throws Exception{
		Path src = new Path("/home/hadoop/data/hello.txt");
		Path dist = new Path("/hdfsapi/test/");
		fs.copyFromLocalFile(src, dist);
	}
	
	
	/*
	 * 5.查看某个目录下所有文件
	 */
	@Test
	public void listFiles()throws Exception{
		FileStatus[]listStatus = fs.listStatus(new Path("/hdfsapi/test"));
		for(FileStatus fileStatus:listStatus) {
			String isDir = fileStatus.isDirectory()?"文件夹":"文件";
			String permission = fileStatus.getPermission().toString();
			short replication = fileStatus.getReplication();
			long len = fileStatus.getLen();
			String path = fileStatus.getPath().toString();
			System.out.println(isDir+"\t"+permission+"\t"+replication+"\t"+len+"\t"+path);
		}	
	}
	
	/*
	 * 6.查看文件块信息
	 */
	@Test
	public void getFileBlockLocations()throws Exception{
		FileStatus fileStatus = fs.getFileStatus(new Path("/hdfsapi/test/b.txt"));
		BlockLocation[]blocks = fs.getFileBlockLocations(fileStatus,0,fileStatus.getLen());
		for(BlockLocation block:blocks) {
			for(String host:block.getHosts()) {
				System.out.println(host);
			}
		}
	}
}
