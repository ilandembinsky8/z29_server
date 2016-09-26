package com.mythings.test;

import java.sql.SQLException;

import com.mythings.db.MyConnection;
import com.mythings.db.NoBidMethods;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		MyConnection con = new MyConnection();
		System.out.println(NoBidMethods.getExample(con.getCon(),"10159","600"));

	}

}
