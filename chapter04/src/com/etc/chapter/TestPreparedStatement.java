package com.etc.chapter;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;


public class TestPreparedStatement {
	public static void insert(Customer cust) {
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/demo";
		Connection conn = null;
		PreparedStatement stmt = null;
		
		
		try {
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, "root", "3306");
			String sql = "insert into customer values(?,?,?,?)";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, cust.getCustname());
			stmt.setString(2, cust.getPwd());
			stmt.setInt(3, cust.getAge());
			stmt.setString(4, cust.getAddress());
			stmt.executeUpdate();	
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
	
	
	
	
	public static void main(String[] args) {
		Customer cust = new Customer("Alice","123",23,"BJ");
		insert(cust);
		
	}

}
