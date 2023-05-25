package hdfs.dev.io;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.hadoop.io.MapFile;


/*
 * 开发详解--5.4.SequenceFile转换为MapFile
 */
public class Convert {
	@SuppressWarnings("deprecation")
	public void testMapFix(String sqlFile)throws Exception{
		String uri = sqlFile;
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		Path path = new Path(uri);
		Path mapData = new Path(path,MapFile.DATA_FILE_NAME);
		Reader reader = new SequenceFile.Reader(fs, mapData,conf);
		Class keyClass = reader.getKeyClass();
		Class  valueClass = reader.getValueClass();
		reader.close();
		long entries = MapFile.fix(fs, path, keyClass, valueClass, false, conf);
		System.out.printf("create MapFile from sequenceFile about %d entries！",entries);	
	}
	
	

	public static void main(String[] args) throws Exception{
		Convert fixer = new Convert();
		fixer.testMapFix(args[0]);
	}

}
