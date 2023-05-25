package com.kafka.lab04.serial;
/*
 * 5.封装一个序列化的工具类
 */

public class SerializeUtils {
	/*
	 * 实现序列化
	 */
	public static byte[]serialize(Object object){
		try {
			return object.toString().getBytes("UTF8");//返回字节数组
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	 * 反序列化
	 */
	public static <T>Object deserialize(byte[]bytes){
		try {
			return new String(bytes,"UTF8");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
