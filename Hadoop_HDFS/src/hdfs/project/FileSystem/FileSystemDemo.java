package hdfs.project.FileSystem;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

/*
 * 项目实战--3.7.分布式文件操作类使用
 */
public class FileSystemDemo {
	public static void main(String[] args) throws IOException{
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://192.168.85.20:9000");
		FileSystem fs = FileSystem.get(conf);
		readFile(fs);
	}
	
	
	//1.读取文件
	public static void readFile(FileSystem fs) throws IOException{
		Path path = new Path("/words.txt");
		FSDataInputStream fis = fs.open(path);
		byte b[]=new byte[200];
		int i = fis.read(b);
		System.out.println(new String(b,0,i));	
	}
	
	//2.创建新文件与写入数据
	public static void createAndWrite(FileSystem fs)throws IOException{
		Path path = new Path("/wordss.txt");
		FSDataOutputStream fos = fs.create(path);
		fos.writeChars("Hello");	
	}
	
	//3.在已有的文件中添加数据
	public static void appendFile(FileSystem fs)throws IOException{
		Path path = new Path("/wordss.txt");
		FSDataOutputStream fos = fs.append(path);
		fos.writeChars("HELLO WORLD");
	}
	
	
	//4.文件重命名
	public static void renameFile(FileSystem fs)throws IOException{
		Path path = new Path("/words.txt");
		boolean flag = fs.rename(path, new Path("/mywords.txt"));
		System.out.println(flag);		
	}
	
	//5.列出路径下对应的文件集
	public static void listPathFile()throws IOException{
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://simple01:9000");
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path("/");
		RemoteIterator<LocatedFileStatus>list = fs.listFiles(path, true);
		while(list.hasNext()) {
			System.out.println(list.next());
		}
	}
		
}