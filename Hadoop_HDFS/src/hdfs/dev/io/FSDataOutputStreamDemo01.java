package hdfs.dev.io;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;



/*
 * Ð´ÈëÎÄ¼þ
 */
public class FSDataOutputStreamDemo01 {
	public static void main(String[] args) throws IOException{
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://simple01:9000");
		FileSystem fs = FileSystem.get(conf);
		FSDataOutputStream fos = fs.create(new Path("/word.txt"));
		fos.writeChars("hello");
	}
}
