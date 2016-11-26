package com.mythings.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ProfileQuery{
	
	private static Statement st;
	private static ResultSet rs;
	
	
	private ProfileQuery(){ super(); }
	
	public static String getAdminData(Connection con, String id) throws SQLException{
		
		st = con.createStatement();
		rs = st.executeQuery("SELECT email FROM MyThingsDB.Users WHERE id="+id);
		if(rs.next())
			return rs.getString(1);
	    return "Email";
	}
	
	public static String updateAdminData(Connection con, String id, String email, String pass) {
		
		try {
			con.createStatement().executeUpdate(
					"UPDATE MyThingsDB.Users"
				  + " SET email='"+email+"',pass='"+pass
				  + "' WHERE id="+id);
			return "Updated successful";
		} catch (SQLException e) {
			return e.getMessage();
		}
	}
}
