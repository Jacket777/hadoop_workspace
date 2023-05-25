package hdfs.project.FileSystem;

import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;
/*
 * ��Ŀʵս--JAVA API����
 */

public class HDFSTest {
	private FileSystem fs = null;
	/*
	 * ��ʼ����������
	 */
	
	@Before
	public void init()throws Exception{
		URI uri = new URI("hdfs://192.168.85.20.9000");//����HDFS
		Configuration conf = new Configuration();//ʹ��HADOOPĬ������
		conf.setBoolean("dfs.support.append", true);
		String user = "root";//��¼�û�
		fs = FileSystem.get(uri, conf, user);	
	}
	
	/*
	 * ����Ŀ¼
	 */
	@Test
	public void testMkdir()throws Exception{
		boolean flag = fs.mkdirs(new Path("/javaApi/mk/dir1/dir2"));
		System.out.println(flag?"�����ɹ�":"����ʧ��");
	}
	
//	/*
//	 * �������ļ��ϴ���HDFS��
//	 */
//	@Test
//	public void testUploadFile()throws Exception{
//		String src = "/simple/hadoop-2.6.4/etc/capacity-scheduler.xml";
//		String hdfsDst = "/javaApi/mk/dir1/";
//	    fs.copyFromLocalFile(new Path(src), new Path(hdfsDst));
//		System.out.println("upload success");
//	}
}
