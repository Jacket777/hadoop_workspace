package hdfs.dev.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;


public class TEST {
	public static void main(String[] args) throws Exception{
	System.out.println("获取文件存放的的节点");
	 getHDFSNodes();
	 System.out.println("获取文件存放的位置");
	 getFileLocal();
	}


public static void getFileLocal() throws Exception {
	Configuration conf = new Configuration();
	conf.set("fs.default.name","hdfs://192.168.150.130:9000");
	conf.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");
	FileSystem hdfs = FileSystem.get(conf);
	Path path = new Path("test4");
 
	FileStatus fileStatus = hdfs.getFileStatus(path);
	BlockLocation[] blkLocations = hdfs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
 
	int blockLen = blkLocations.length;
    for(int i = 0 ; i < blockLen ; ++i ){
      String[] hosts = blkLocations[i].getHosts();
      System.out.println("block_"+i + "_location:" + hosts[i]);
      System.out.println("==========");
    }
 }
 

public static void getHDFSNodes() throws Exception{
	Configuration conf = new Configuration();
	conf.set("fs.default.name","hdfs://192.168.150.130:9000");
	conf.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");

 FileSystem fs = FileSystem.get(conf);
 DistributedFileSystem hdfs = (DistributedFileSystem)fs;
 DatanodeInfo[] dataNodeStats = hdfs.getDataNodeStats();
 
for( int i = 0 ; i < dataNodeStats.length ; ++i ){
 System.out.println("DataNode_" + i + "_Node:" + dataNodeStats[i].getHostName());
 }
 }
 }
