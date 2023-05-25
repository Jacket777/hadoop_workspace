package hdfs.dev.FileSystem;
import java.net.URI;
import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/*
 * 开发详解--4.2.FileSystem以标准方式读取系统文件
 */

public class FileSystemReader {
	public static void main(String[] args) throws IOException{
		Configuration conf = new Configuration();
		String uri = args[0];
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		
		InputStream inputStream = null;
		try {
			inputStream = fs.open(new Path(uri));
			IOUtils.copyBytes(inputStream, System.out, 4096, false);
			} catch (IllegalArgumentException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			}finally {
			IOUtils.closeStream(inputStream);
			}
	}
}