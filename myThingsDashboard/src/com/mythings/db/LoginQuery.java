package com.mythings.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginQuery {

	private static Statement st;
	private static ResultSet rs;
	
	private LoginQuery(){}
	
	
	public static boolean checkID(Connection con, String id) throws NumberFormatException, SQLException{
		
		st =  con.createStatement();
		rs = st.executeQuery("SELECT * FROM Users where id="+ Integer.parseInt(id));
		return rs.next();
	}
	
	
	public static boolean checkPass(Connection con, String id, String pass) throws SQLException{
		
		st =  con.createStatement();
		rs = st.executeQuery("SELECT * FROM Users where id="+ Integer.parseInt(id) + " AND pass='" + pass + "';");
		return rs.next();
	}
}
