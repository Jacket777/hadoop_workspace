package com.etc.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.etc.dao.JDBCConnectionFactory;


public class CounterService {
	public void save(int number) {
		Connection connection = JDBCConnectionFactory.getConnection();
		Statement statement = null;
		String sql ="update counter set number="+number;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(statement!=null) {
				try {
					statement.close();
				}catch (Exception e) {
					e.printStackTrace();
				}	
			}
			
			if(connection!=null) {
				try {
					connection.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public Integer getNumber() {
		Connection connection = JDBCConnectionFactory.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		
		String sql ="select * from counter";
		Integer number = 0;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				number = resultSet.getInt(1);
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(resultSet!=null) {
				try {
					resultSet.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(statement!=null) {
				try {
				statement.close();	
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			if(connection!=null) {
				try {
					connection.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return number;
	}
}