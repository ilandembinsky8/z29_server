package com.mythings.test;

import java.sql.SQLException;
import com.mythings.db.KpiQuery;
import com.mythings.db.MyConnection;


public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		MyConnection con = new MyConnection();
		System.out.println(KpiQuery.getAdv(con.getCon(),"10159"));
	}
}
