package hdfs.dev.io;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FSDataInputStream;

/*
 * 读出一个文件的数据
 */
public class FSDataInputStreamDemo01 {
	public static void main(String[] args) throws IOException{
		Configuration conf =new Configuration();
		conf.set("fs.defaultFS", "hdfs://simple01:9000/");
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path("/words.txt");
		FSDataInputStream fis = fs.open(path);
		byte b[] = new byte[200];
		int i = fis.read();
		System.out.println(new String(b,0,i));
	}
}