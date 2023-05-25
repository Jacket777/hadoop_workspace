package hdfs.project.FileSystem;

import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;
/*
 * 项目实战--JAVA API操作
 */

public class HDFSTest {
	private FileSystem fs = null;
	/*
	 * 初始化环境变量
	 */
	
	@Before
	public void init()throws Exception{
		URI uri = new URI("hdfs://192.168.85.20.9000");//连接HDFS
		Configuration conf = new Configuration();//使用HADOOP默认配置
		conf.setBoolean("dfs.support.append", true);
		String user = "root";//登录用户
		fs = FileSystem.get(uri, conf, user);	
	}
	
	/*
	 * 创建目录
	 */
	@Test
	public void testMkdir()throws Exception{
		boolean flag = fs.mkdirs(new Path("/javaApi/mk/dir1/dir2"));
		System.out.println(flag?"创建成功":"创建失败");
	}
	
//	/*
//	 * 将本地文件上传到HDFS上
//	 */
//	@Test
//	public void testUploadFile()throws Exception{
//		String src = "/simple/hadoop-2.6.4/etc/capacity-scheduler.xml";
//		String hdfsDst = "/javaApi/mk/dir1/";
//	    fs.copyFromLocalFile(new Path(src), new Path(hdfsDst));
//		System.out.println("upload success");
//	}
}
