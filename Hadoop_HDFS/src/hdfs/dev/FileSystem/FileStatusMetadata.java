package hdfs.dev.FileSystem;
import java.net.URI;
import java.sql.Timestamp;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/*
 * 开发详解--4.3.FileStatue获取文件或目录的源数据信息
 */

public class FileStatusMetadata {
	@SuppressWarnings("deprecation")
	public static void main(String[] args)throws Exception {
		Configuration conf = new Configuration();
		conf.set("hadoop.job.ugi", "jack");
		//实验1：查看HDFS中某文件的元信息
		System.out.println("实验1：查看HDFS中某文件的元信息");
		String uri = args[0];
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		FileStatus fileStatus = fs.getFileStatus(new Path(uri));
		if(fileStatus.isDir()== false) {
			System.out.println("这是个文件");
		}
		System.out.println("文件路径: "+fileStatus.getPath());
		System.out.println("文件长度: "+fileStatus.getLen());
		System.out.println("文件修改日期: "+
		new Timestamp(fileStatus.getModificationTime()).toString());
		System.out.println("文件上次访问日期: "+
		new Timestamp(fileStatus.getAccessTime()).toString());
		System.out.println("文件备份数: "+fileStatus.getReplication());
		System.out.println("文件的块大小: "+fileStatus.getBlockSize());
		System.out.println("文件所有者: "+fileStatus.getOwner());
		System.out.println("文件所在的分组: "+fileStatus.getGroup());
		System.out.println("文件的权限: "+fileStatus.getPermission().toString());
		System.out.println();
		
		//实验1：查看HDFS中某目录的元信息
		System.out.println("实验1：查看HDFS中某目录的元信息");
		String dirUri = args[1];
		FileSystem dirFS = FileSystem.get(URI.create(dirUri), conf);
		FileStatus dirStatus = dirFS.getFileStatus(new Path(dirUri));
		if(dirStatus.isDir()== true) {
			System.out.println("这是个目录");
		}
		System.out.println("目录路径: "+dirStatus.getPath());
		System.out.println("目录长度: "+dirStatus.getLen());
		System.out.println("目录修改日期: "+
		new Timestamp(dirStatus.getModificationTime()).toString());
		System.out.println("目录上次访问日期: "+
		new Timestamp(dirStatus.getAccessTime()).toString());
		System.out.println("目录备份数: "+dirStatus.getReplication());
		System.out.println("目录的块大小: "+dirStatus.getBlockSize());
		System.out.println("目录所有者: "+dirStatus.getOwner());
		System.out.println("目录所在的分组: "+dirStatus.getGroup());
		System.out.println("目录的权限: "+dirStatus.getPermission().toString());
		System.out.println("这个目录包含以下文件或目录: ");
		for(FileStatus fsA:dirFS.listStatus(new Path(dirUri))) {
			System.out.println(fsA.getPath());
			
		}
		System.out.println();
	}
}
