package com.javatpoint.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection 
{
    private Connection connection;
	public Connection getConnection()
		{
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			connection=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/jersey","root","shivamagarwal");  
			}catch(Exception e){ System.out.println(e);} 
		return this.connection;
	   }

	public void closeConnection(){	
		try {
			this.connection.close();
		} catch (SQLException e) {
		System.out.println(e);
		}
	}
}