package com.zhongzhou.Excavator.DAO.MSSQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class TestConnection {
	
	@Test
	public void testConnection() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String URL = "jdbc:sqlserver://192.168.0.10:1433;DatabaseName=fttxrun"; 
		
		String userName = "csidbo"; //默认用户名
		String userPwd = "zll19991219"; //密码
		Connection dbConn = DriverManager.getConnection( URL, userName, userPwd);
		
		if( dbConn != null ){
			System.out.println( "Connection Successful! ");
			
			dbConn.close();
		}
	}
	
}
