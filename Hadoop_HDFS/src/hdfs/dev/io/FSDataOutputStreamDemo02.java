package hdfs.dev.io;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FSDataOutputStream;

/*
 * 文件末尾添加数据
 */
public class FSDataOutputStreamDemo02 {
	public static void main(String[] args) throws IOException{
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://simple01:9000");
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path("/words.txt");
		FSDataOutputStream fss = fs.append(path);
		fss.writeChars("Jack");	
	}
}