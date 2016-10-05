package com.mythings.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mythings.db.KpiQuery;
import com.mythings.db.MyConnection;
import com.mythings.db.NoBidQuery;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		MyConnection con = new MyConnection();

		//System.out.println( KpiQuery.getAdvCompaign(con.getCon(), "10159", 183, "dsfsdf", "sdfsfd") );
		
		System.out.println(NoBidQuery.getNoBidReasons(con.getCon(),"10159"));
		
		
	}
}
