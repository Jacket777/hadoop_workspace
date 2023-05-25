package com.hive.demo01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/*
 * Hive JDBC����ʵ��
 * 1.����ɾ��load���ݣ���ѯ
 */

public class HiveJdbcClient {
	private static String dirverName="org.apache.hive.jdbc.HiveDriver";
	private static String url="jdbc:hive2://192.168.150.130:10000/default";
	private static String user="root";
	private static String password="root";
	private static String sql="";
	private static ResultSet res;
	private static final Logger log = Logger.getLogger(HiveJdbcClient.class);
	
	
	public static void main(String[] args) {
		try {
			Class.forName(dirverName);
			Connection conn = DriverManager.getConnection(url,user,password);
			Statement stmt = conn.createStatement();
			
			String tableName = "testHiveDriverTable";//1.�����ı���
			sql = "drop table "+tableName;
			stmt.executeUpdate(sql);//2.���ڸñ���ɾ���ñ�
			sql = "create table "+tableName+" (key int,value string) row format"
					+ " delimited fields terminated by '\t'";
			stmt.executeUpdate(sql);//3.�����ڵľʹ����ñ�
			
			//1.ִ��show tables ����
			sql = "show tables '"+tableName+"'";
			System.out.println("Running: "+sql);
			res = stmt.executeQuery(sql);
			System.out.println("ִ�� show tables ���н����");
			if(res.next()) {
				System.out.println(res.getString(1));
			}
			
			
			//2.ִ��describe tables ����
			sql = "describe "+tableName;
			System.out.println("Running: "+sql);
			res = stmt.executeQuery(sql);
			System.out.println("ִ�� describe table ���н��: ");
			while(res.next()) {
				System.out.println(res.getString(1)+"\t"+res.getString(2));
			}
			
			
			//3.ִ��load data into table ����
			String filepath = "/simple/userinfo.txt";
			sql = "load data local inpath '"+filepath+"' into table "+tableName;
			System.out.println("Running: "+sql);
			stmt.executeUpdate(sql);
			
			
			//4.ִ�� select * query ����
			sql = "select * from "+tableName;
			System.out.println("Running: "+sql);
			res = stmt.executeQuery(sql);
			System.out.println("ִ�� select * query ���н��: ");
			while(res.next()) {
				System.out.println(res.getInt(1)+"\t"+res.getString(2));	
			}
			
			
			//5.ִ��regular hive query ����
			sql =  "select count(1) from "+tableName;
			System.out.println("Running: "+sql);
			res = stmt.executeQuery(sql);
			System.out.println("ִ��regular hive query ���н���� ");
			while(res.next()) {
				System.out.println(res.getString(1));
			}
			
			conn.close();
			conn = null;	
		}catch(SQLException e) {
			e.printStackTrace();
			log.error("Connection error! ",e);
			System.exit(1);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			log.error(dirverName+" not found! ",e);
			System.exit(1);
		}
	}
}
