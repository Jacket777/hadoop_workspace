package com.hive.demo02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Hive {
	public static void testTask(){
		Connection con= null;
		Statement stmt = null;
		ResultSet res = null;
		
	try {
		 Class.forName(ConfigInfo.dirverName);    	 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1); }
       
		
	  try {
        con = DriverManager.getConnection(ConfigInfo.url, ConfigInfo.user, ConfigInfo.password);
        if(con==null) {
        	System.out.println("===can not get connection===");
        } 
        stmt = con.createStatement();
        System.out.println("Running: " + ConfigInfo.sql);
		res = stmt.executeQuery(ConfigInfo.sql);
		while (res.next()) {
		    System.out.println(res.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(res!=null) {
				res = null;
			}
			if(stmt!=null) {
				stmt = null;
			}
			if(con!=null) {
				con = null;
			}
		}
 }
}
