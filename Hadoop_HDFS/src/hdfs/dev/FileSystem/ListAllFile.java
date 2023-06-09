package hdfs.dev.FileSystem;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileUtil;
import java.net.URI;

/*
 * 开发详解--4.7.列出目录下所有的文件
 */
public class ListAllFile {
	public static void main(String[] args) throws Exception{
		String uri = args[0];
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		Path[]paths = new Path[args.length];
		for(int i =0;i <paths.length;i++) {
			paths[i]=new Path(args[i]);
		}
		FileStatus[]status = fs.listStatus(paths);
		Path[]listedPaths = FileUtil.stat2Paths(status);
		for(Path p: listedPaths) {
			System.out.println(p);
		}
	}
}
