package hdfs.dev.io;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;
import java.net.URI;

/*
 * 开发详解--5.2.SequenceFile读操作
 */
public class SeqRead {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create("hdfs://localhost:9000"), conf);
		Path path = new Path("test.seq");
		SequenceFile.Reader reader = null;
		try {
			reader = new SequenceFile.Reader(fs, path, conf);
			Writable key =(Writable)ReflectionUtils.newInstance(reader.getKeyClass(), conf);
			Writable value = (Writable)ReflectionUtils.newInstance(reader.getValueClass(), conf);
			while(reader.next(key, value)) {
				System.out.println(key+"\t"+value);
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeStream(reader);
		}	
	}
}
