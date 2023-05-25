package com.hive.demo02;

public class ConfigInfo {
	    //1.hive驱动名称
		public static String dirverName = "org.apache.hive.jdbc.HiveDriver";
	     //2.连接hive服务的连接地址
		public static String url="jdbc:hive2://10.47.199.166:10000/srdss";
		//3.用户
		public static String user="bigdata";
		//4.密码
		public static String password= "bigdata";
		//5.执行的sql
		public static String sql ="select  * from why limit 100";
}
