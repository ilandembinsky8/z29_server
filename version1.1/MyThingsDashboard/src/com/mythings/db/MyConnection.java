package com.mythings.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyConnection {
	
	private Connection con;
	private final String url = "jdbc:mysql://tsofen29db.csks0itfccec.eu-central-1.rds.amazonaws.com:3306";
	private final String username = "Tsofen29DBUser";
	private final String pass = "Tsofen29SQL";
	
	public MyConnection() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url ,username ,pass);
	}
	
	public Connection getCon(){ return con; }
	
	public void closeCon() throws SQLException { con.close(); }

}
