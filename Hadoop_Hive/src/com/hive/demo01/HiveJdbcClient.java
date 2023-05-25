package com.hive.demo01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/*
 * Hive JDBC操作实例
 * 1.建表，删表，load数据，查询
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
			
			String tableName = "testHiveDriverTable";//1.创建的表名
			sql = "drop table "+tableName;
			stmt.executeUpdate(sql);//2.存在该表则删除该表
			sql = "create table "+tableName+" (key int,value string) row format"
					+ " delimited fields terminated by '\t'";
			stmt.executeUpdate(sql);//3.不存在的就创建该表
			
			//1.执行show tables 操作
			sql = "show tables '"+tableName+"'";
			System.out.println("Running: "+sql);
			res = stmt.executeQuery(sql);
			System.out.println("执行 show tables 运行结果：");
			if(res.next()) {
				System.out.println(res.getString(1));
			}
			
			
			//2.执行describe tables 操作
			sql = "describe "+tableName;
			System.out.println("Running: "+sql);
			res = stmt.executeQuery(sql);
			System.out.println("执行 describe table 运行结果: ");
			while(res.next()) {
				System.out.println(res.getString(1)+"\t"+res.getString(2));
			}
			
			
			//3.执行load data into table 操作
			String filepath = "/simple/userinfo.txt";
			sql = "load data local inpath '"+filepath+"' into table "+tableName;
			System.out.println("Running: "+sql);
			stmt.executeUpdate(sql);
			
			
			//4.执行 select * query 操作
			sql = "select * from "+tableName;
			System.out.println("Running: "+sql);
			res = stmt.executeQuery(sql);
			System.out.println("执行 select * query 运行结果: ");
			while(res.next()) {
				System.out.println(res.getInt(1)+"\t"+res.getString(2));	
			}
			
			
			//5.执行regular hive query 操作
			sql =  "select count(1) from "+tableName;
			System.out.println("Running: "+sql);
			res = stmt.executeQuery(sql);
			System.out.println("执行regular hive query 运行结果： ");
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
