package tsofen.mythings.nobid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import tsofen.course.db.MyConnection;

public class NoBidMethods {

	
	
	private NoBidMethods(){}
	
	
	
	public static String getExchange(Connection con) throws SQLException{
		
		Statement st = null;
		 ResultSet rs;
		 JsonArray jArr;
		 JsonObject jObj;
		
		
       jArr = new JsonArray();
		
		rs = st.executeQuery("SELECT * FROM Updated.PRT_CAMPAIGN;");
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
		con.close();
		
        return jArr.toString();
	}	
}
