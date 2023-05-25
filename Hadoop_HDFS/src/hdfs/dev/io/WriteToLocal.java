package hdfs.dev.io;

import java.io.IOException;
import java.net.URI;
import java.io.OutputStream;
import java.net.URISyntaxException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RawLocalFileSystem;


/*
 * 开发详解--5.5.客户端校验类：自定义文件上传校验
 */

public class WriteToLocal {
	public static void main(String[] args) throws IOException,URISyntaxException {
		Configuration conf = new Configuration();
		LocalFileSystem fs = new LocalFileSystem(new RawLocalFileSystem());
		fs.initialize(new URI("file:///home/jack/file1.txt"), conf);
		OutputStream out = fs.create(new Path("file:///home/jack/file1.txt"));
		for(int i = 0;i<512*10;i++) {
			out.write(97);
		}
		out.close();
		Path file = fs.getChecksumFile(new Path("file:///home/jack/file1.txt"));
		System.out.println(file.getName());
		fs.close();
	}
}
