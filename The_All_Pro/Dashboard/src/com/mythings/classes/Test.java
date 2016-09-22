package com.mythings.classes;

import java.sql.SQLException;

public class Test {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		DBManager dbm = DBManager.getInstance();
		dbm.runProcFromDB();
		dbm.closeConToDB();
	}
}
