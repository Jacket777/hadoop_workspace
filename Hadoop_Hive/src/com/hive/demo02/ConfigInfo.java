package com.hive.demo02;

public class ConfigInfo {
	    //1.hive��������
		public static String dirverName = "org.apache.hive.jdbc.HiveDriver";
	     //2.����hive��������ӵ�ַ
		public static String url="jdbc:hive2://10.47.199.166:10000/srdss";
		//3.�û�
		public static String user="bigdata";
		//4.����
		public static String password= "bigdata";
		//5.ִ�е�sql
		public static String sql ="select  * from why limit 100";
}
