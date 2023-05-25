package com.ch03.hdfs;

import java.net.URL;
import java.io.InputStream;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;

//example -1 :PASS
public class URLCat {
	static {
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());	
	}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		InputStream in = null;
		try {
			in = new URL(args[0]).openStream();
			IOUtils.copyBytes(in, System.out, 4096, false);
		}finally {
			IOUtils.closeStream(in);
		}
	}
}