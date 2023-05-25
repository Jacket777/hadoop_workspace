package hdfs.dev.FileSystem;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.log4j.Logger;



/*
 * �������--4.5.FSDataInputStream��
 * ʵ��HDFS�ϴ����Ͷ�ȡ�ļ�����
 */

public class HDFSDataInputExample {
	static final Logger logger = Logger.getLogger(HDFSDataInputExample.class);
	public static void main(String[] args) throws IOException {
		HDFSDataInputExample sample = new HDFSDataInputExample();
		String cmd = args[0];
		String localPath = args[1];
		String hdfsPath = args[2];
		if("create".equals(cmd)) {
			sample.createFile(localPath, hdfsPath);
		}else if("get".equals(cmd)) {
			boolean print = Boolean.parseBoolean(args[3]);
			sample.getFile(localPath, hdfsPath, print);
		}
	}
	
	/*
	 * �����ļ�
	 */
	public void createFile(String localPath,String hdfsPath)throws IOException{
		InputStream in = null;
		try{
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
			FSDataOutputStream out = fs.create(new Path(hdfsPath));
			in = new BufferedInputStream(new FileInputStream(new File(localPath)));
			IOUtils.copyBytes(in, out, 4096,false);
			out.hsync();
			out.close();
			logger.info("create file in hdfs: "+hdfsPath);	
		}finally {
			IOUtils.closeStream(in);
		}	
	}
	
	
	/*
	 * ��ȡHDFS�ļ�
	 */
	public void getFile(String localPath,String hdfsPath,boolean print)throws IOException{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		FSDataInputStream in =null;
		OutputStream out = null;
		try {
			in = fs.open(new Path(hdfsPath));
			out = new BufferedOutputStream(new FileOutputStream(new File(localPath)));
			IOUtils.copyBytes(in, out, 4096, !print);
			logger.info("get file from hdfs to local: "+hdfsPath+" ,"+localPath);
			if(print) {
				in.seek(0);
				IOUtils.copyBytes(in, System.out, 4096,true);
			}
		}finally {
			IOUtils.closeStream(out);
		}	
	}

}
