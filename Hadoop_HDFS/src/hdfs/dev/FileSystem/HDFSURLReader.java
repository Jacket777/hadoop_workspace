package hdfs.dev.FileSystem;
import java.io.InputStream;
import java.net.URL;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;


/*
 * 应用开发详解--4.1.Hadoop URL读取数据
 */
public class HDFSURLReader {
	static {
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
	}
	
	public static void main(String[] args) {
		InputStream inputStream = null;
		try {
			inputStream = new URL(args[0]).openStream();
			IOUtils.copyBytes(inputStream, System.out, 1024,false);
		}catch(Exception e) {
			IOUtils.closeStream(inputStream);
		}
	}
}