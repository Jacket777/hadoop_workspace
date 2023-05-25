package com.hdfs.unit01.rpc;
/*
 * 项目实战--定义Isay接口
 */
public interface Isay {
	/*
	 * 定义say方法
	 * @param userName 用户姓名
	 * @return 回应语
	 */
	public  static final long versionID = 1L;
	public String say(String userName);
}
