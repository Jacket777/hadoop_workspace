package hdfs.dev.io;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

/*
 * 开发详解--5.1.SequenceFile写操作
 */

public class SeqWrite {
	private static final String[]data= {
			"a,b,c,d,e,f,g","h,i,j,k,l,m,n","o,p,q,r,s,t","0,1,2,3,4","5,6,7,8,9"				
	};

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create("hdfs://localhost:9000"), conf);
		Path path = new Path("test.seq");//序列化文件路径
		//打开SequenceFile.Writer对象，写入键值
		SequenceFile.Writer writer = null;
		IntWritable key = new IntWritable();
		Text value = new Text();
		try {
		writer = SequenceFile.createWriter(fs, conf, path, key.getClass(), value.getClass());
		for(int i=0;i<1000;i++) {
			key.set(i);
			value.set(SeqWrite.data[i%SeqWrite.data.length]);
			writer.append(key, value);	
		}
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		IOUtils.closeStream(writer);
	}
  }
}
