package com.hdfs.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/*
 * 序列化工具类
 */

public class HadoopSerializationUtil {
	public static byte[]serialize(Writable writable)
			throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataout = new DataOutputStream(out);
		writable.write(dataout);
		dataout.close();
		return out.toByteArray();
	}
	
	public static void deserialize(Writable writable,byte[]bytes)
			throws Exception{
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream datain = new DataInputStream(in);
		writable.readFields(datain);
		datain.close();	
	}

}
