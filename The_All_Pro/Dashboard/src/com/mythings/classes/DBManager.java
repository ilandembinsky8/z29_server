package com.mythings.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DBManager {

	private static DBManager _instance = null;
	private final String url = "jdbc:mysql://tsofen29db.csks0itfccec.eu-central-1.rds.amazonaws.com:3306/Updated";
	private final String username = "Tsofen29DBUser";
	private final String pass = "Tsofen29SQL";
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private JsonArray jArr;
	private JsonObject jObj;
	
	// Where is written Ex that's mean Exchange
	// and where is written Adv that's says Advertiser 
	
	private void connectToDB() throws SQLException{
		con = DriverManager.getConnection(url ,username ,pass);
		st =  con.createStatement();
	}
	
	private DBManager() throws ClassNotFoundException, SQLException{
		// MySql JDBC driver from mysql.com
		Class.forName("com.mysql.jdbc.Driver");
		connectToDB();
	}
	
	public static DBManager getInstance() throws ClassNotFoundException, SQLException{
		if(_instance == null)
			_instance = new DBManager();
		return _instance;
	}

	public String getEx() throws SQLException{
		
		jArr = new JsonArray();
		
		//rs = st.executeQuery("SELECT * FROM Exchanges;");
		rs = st.executeQuery("SELECT * FROM ADV_CAMPAIGN;");
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
        return jArr.toString();
	}
	
	public boolean checkID(String id) throws NumberFormatException, SQLException{
		
		rs = st.executeQuery("SELECT * FROM Admin where id="+ Integer.parseInt(id));
		return rs.next();
	}
	
	public boolean checkPass(String id, String pass) throws SQLException{
		
		rs = st.executeQuery("SELECT * FROM Admin where id="+ Integer.parseInt(id) + " AND pass='" + pass + "';");
		return rs.next();
	}
	
	public String getAdv() throws SQLException{
		
		jArr = new JsonArray();
		
		rs = st.executeQuery("SELECT * FROM Advertisers;");
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}

        return jArr.toString();
	}
	
	public String getAdvForEx(int exID) throws SQLException{
		
		jArr = new JsonArray();
		
		rs = st.executeQuery("SELECT Advertisers.id, Advertisers.name " 
				           + "FROM Advertisers "
				           + "INNER JOIN Ex_Adv "
				           + "ON Advertisers.id=Ex_Adv.Advertiser_ID "
				           + "WHERE Ex_Adv.Exchange_ID=" + exID);
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}

        return jArr.toString();
	}
	
	// run the latest query inside the procedure at database 
	public void runProcFromDB() throws SQLException{
		
		rs = st.executeQuery("select distinct  REASON_TYPE.NO_BID_REASON_TYPE_ID , REASON_TYPE.NO_BID_REASON_TYPE,NOBID_MAINTABLE.ADV_PROJECT_ID, ADV_PROJECT.ADV_PROJECT_NAME "
					       + "from REASON_TYPE inner join NOBID_MAINTABLE on NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID=REASON_TYPE.NO_BID_REASON_TYPE_ID "
						   + "inner join ADV_PROJECT on NOBID_MAINTABLE.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID "
						   + "where NOBID_MAINTABLE.PRT_CAMPAIGN_ID=10159 "
						   + "order by NO_BID_REASON_TYPE_ID;");
		while(rs.next())
			System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
	}
	
	public void closeConToDB() throws SQLException{
		rs.close();
		st.close();
		con.close();
		_instance = null;
	}
}