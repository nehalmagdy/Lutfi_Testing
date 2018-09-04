package com.database.lutfi

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

import com.kms.katalon.core.annotation.Keyword
import com.mysql.jdbc.Statement

public class Database {

	private static Connection connection = null;
	@Keyword
	def connectDB(String url, String dbname, String port, String username, String password) {
		String conn = "jdbc:mysql://"+url+":"+port+"/"+dbname

		if(connection !=null && !connection.isClosed())
			connection.close()

		connection = DriverManager.getConnection(conn,username,password)
		return connection
	}
	@Keyword
	def closeDatabaseConnection() {
		if(connection !=null && !connection.isClosed())
			connection.close()

		connection = null;
	}
	@Keyword
	def executeQuery(String queryString) {
		Statement stm = connection.createStatement()
		ResultSet rs = stm.executeQuery(queryString)
		return rs
	}
	@Keyword
	def execute(String queryString) {
		Statement stm = connection.createStatement()
		boolean result = stm.execute(queryString)
		return result
	}
}
