package com.etc.chapter;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;


public class TestInsert {

	public static void main(String[] args) {
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/demo";
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, "root", "3306");
			stmt=conn.createStatement();
			String sql = "insert into customer values('John','123',34,'HK')";
			stmt.execute(sql);	
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(stmt!=null) {
				try {
					stmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(conn!=null) {
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			
			
			
		}
		

	}

}
