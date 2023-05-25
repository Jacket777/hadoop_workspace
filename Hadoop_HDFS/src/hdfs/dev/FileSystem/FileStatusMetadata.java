package hdfs.dev.FileSystem;
import java.net.URI;
import java.sql.Timestamp;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/*
 * �������--4.3.FileStatue��ȡ�ļ���Ŀ¼��Դ������Ϣ
 */

public class FileStatusMetadata {
	@SuppressWarnings("deprecation")
	public static void main(String[] args)throws Exception {
		Configuration conf = new Configuration();
		conf.set("hadoop.job.ugi", "jack");
		//ʵ��1���鿴HDFS��ĳ�ļ���Ԫ��Ϣ
		System.out.println("ʵ��1���鿴HDFS��ĳ�ļ���Ԫ��Ϣ");
		String uri = args[0];
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		FileStatus fileStatus = fs.getFileStatus(new Path(uri));
		if(fileStatus.isDir()== false) {
			System.out.println("���Ǹ��ļ�");
		}
		System.out.println("�ļ�·��: "+fileStatus.getPath());
		System.out.println("�ļ�����: "+fileStatus.getLen());
		System.out.println("�ļ��޸�����: "+
		new Timestamp(fileStatus.getModificationTime()).toString());
		System.out.println("�ļ��ϴη�������: "+
		new Timestamp(fileStatus.getAccessTime()).toString());
		System.out.println("�ļ�������: "+fileStatus.getReplication());
		System.out.println("�ļ��Ŀ��С: "+fileStatus.getBlockSize());
		System.out.println("�ļ�������: "+fileStatus.getOwner());
		System.out.println("�ļ����ڵķ���: "+fileStatus.getGroup());
		System.out.println("�ļ���Ȩ��: "+fileStatus.getPermission().toString());
		System.out.println();
		
		//ʵ��1���鿴HDFS��ĳĿ¼��Ԫ��Ϣ
		System.out.println("ʵ��1���鿴HDFS��ĳĿ¼��Ԫ��Ϣ");
		String dirUri = args[1];
		FileSystem dirFS = FileSystem.get(URI.create(dirUri), conf);
		FileStatus dirStatus = dirFS.getFileStatus(new Path(dirUri));
		if(dirStatus.isDir()== true) {
			System.out.println("���Ǹ�Ŀ¼");
		}
		System.out.println("Ŀ¼·��: "+dirStatus.getPath());
		System.out.println("Ŀ¼����: "+dirStatus.getLen());
		System.out.println("Ŀ¼�޸�����: "+
		new Timestamp(dirStatus.getModificationTime()).toString());
		System.out.println("Ŀ¼�ϴη�������: "+
		new Timestamp(dirStatus.getAccessTime()).toString());
		System.out.println("Ŀ¼������: "+dirStatus.getReplication());
		System.out.println("Ŀ¼�Ŀ��С: "+dirStatus.getBlockSize());
		System.out.println("Ŀ¼������: "+dirStatus.getOwner());
		System.out.println("Ŀ¼���ڵķ���: "+dirStatus.getGroup());
		System.out.println("Ŀ¼��Ȩ��: "+dirStatus.getPermission().toString());
		System.out.println("���Ŀ¼���������ļ���Ŀ¼: ");
		for(FileStatus fsA:dirFS.listStatus(new Path(dirUri))) {
			System.out.println(fsA.getPath());
			
		}
		System.out.println();
	}
}
