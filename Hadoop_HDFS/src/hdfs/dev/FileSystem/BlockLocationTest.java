package hdfs.dev.FileSystem;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
/*
 * �������--4.4.����ĳ���ļ���HDFS��Ⱥ��λ��
 */

public class BlockLocationTest {
	public static void main(String[] args)throws Exception {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(args[0]);
		FileStatus fileStatus = fs.getFileStatus(path);
		BlockLocation[]blkLocation = fs.getFileBlockLocations(fileStatus, 0,
				fileStatus.getLen());
		int blockLen = blkLocation.length;
		for(int i = 0;i<blockLen;i++) {
			String[]hosts = blkLocation[i].getHosts();
			System.out.println("block_"+i+"_location:"+hosts[0]);
		}
	}
}