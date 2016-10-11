package com.mythings.test;

import java.sql.Connection;
import java.sql.SQLException;

import com.mythings.db.KpiQuery;
import com.mythings.db.MyConnection;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		MyConnection con = new MyConnection();		
		Connection connection = con.getCon();
		System.out.println(KpiQuery.getDates(connection));
		
		
		//System.out.println( KpiQuery.getAdGroup(con.getCon(), "10159", 183,4766, "dsfsdf", "sdfsfd") );
	}
}
