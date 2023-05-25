package hdfs.dev.FileSystem;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import java.io.IOException;

/*
 * �������--4.8. ����������ļ���������ʵ��
 */

public class FileFilter implements PathFilter {
	private String matcher = "*";
	private FileSystem fs = null;
	
	public void setMatcher(String matcher) {
		this.matcher = matcher;
	}

	public void setFs(FileSystem fs) {
		this.fs = fs;
	}
	
	//�ļ�·������
	public boolean accept(String filePath) {
		boolean isMatcherAll = matcher.equals("*");
		boolean isStrIndexOf = (filePath.indexOf(matcher)>=0);
		if(isMatcherAll ||isStrIndexOf) {
			return true;
		}
		try {
			if(filePath.matches(matcher)) {
				return true;
			}
		}catch(Exception e) {
			System.err.println(FileFilter.class.getName()+" err for "+matcher);
			return true;
		}
		return false;
	}



	//���������ļ��������ļ���
	@Override
	public boolean accept(Path path) {
		//���������ļ��������ļ���
		String fileName = path.getName();
		if(fileName.endsWith(".temp")||fileName.endsWith(".crc")) {
			return false;
		}
		//�����Ŀ¼��������������
		try {
			if(fs.getFileStatus(path).isDirectory()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return accept(path.toString());
	}
}
