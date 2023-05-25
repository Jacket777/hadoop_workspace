package hdfs.dev.FileSystem;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import java.io.IOException;

/*
 * 开发详解--4.8. 基于正则的文件过滤器器实现
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
	
	//文件路径过滤
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



	//过滤隐藏文件或隐藏文件夹
	@Override
	public boolean accept(Path path) {
		//过滤隐藏文件或隐藏文件夹
		String fileName = path.getName();
		if(fileName.endsWith(".temp")||fileName.endsWith(".crc")) {
			return false;
		}
		//如果是目录则跳过，不过滤
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
