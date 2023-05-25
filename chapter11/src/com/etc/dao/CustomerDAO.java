package com.etc.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.etc.vo.Customer;


public class CustomerDAO {
	
	public ArrayList<Customer>selectAll(){
		ArrayList<Customer>list = new ArrayList<Customer>();
		Connection conn = JDBCConnectionFactory.getConnection();
		try {
			Statement statement = conn.createStatement();
			String sql = "select custname,age,address from customer";
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				list.add(new Customer(resultSet.getString(1), null,resultSet.getInt(2), resultSet.getString(3)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}	
		}
		return list;
	}
	
	
	public Customer selectByName(String custname) {
		Customer cust=null;
		Connection conn= JDBCConnectionFactory.getConnection();
		String sql = "select * from customer where custname=?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, custname);
			ResultSet rs= pstmt.executeQuery();
			if(rs.next()) {
				cust = new Customer(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cust;
	}
	
	
	public Customer selectByNamePwd(String custname,String pwd) {
		Customer cust=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Connection conn= JDBCConnectionFactory.getConnection();
		String sql = "select * from customer where custname=? and pwd=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, custname);
			pstmt.setString(2, pwd);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				cust = new Customer(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cust;	
	}
	
	public void  insert(Customer cust) {
		Connection conn= JDBCConnectionFactory.getConnection();
		String sql = "insert into customer values(?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cust.getName());
			pstmt.setString(2, cust.getPwd());
			pstmt.setInt(3, cust.getAge());
			pstmt.setString(4, cust.getAddress());			
			pstmt.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
