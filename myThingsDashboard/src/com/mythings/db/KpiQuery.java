package com.mythings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class KpiQuery {
	
	private static Statement st;
	private static ResultSet rs;
	private static JsonArray jArr;
	private static JsonObject jObj;
	private static String query;
	private static PreparedStatement pst;
	
	private KpiQuery(){}
	
	
	public static String getExchange(Connection con) throws SQLException{
		
		st =  con.createStatement();
		
		jArr = new JsonArray();
		rs = st.executeQuery("SELECT * FROM PRT_CAMPAIGN;");
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
		
        return jArr.toString();
	}	
	
	
	public static String getAdv(Connection con,String exchId) throws SQLException{
		
		jArr = new JsonArray();
		int id = Integer.parseInt(exchId);
	
		query = "SELECT distinct ADV_PROJECT.ADV_PROJECT_ID , ADV_PROJECT.ADV_PROJECT_NAME"
			  + " FROM ADV_PROJECT , KPI_MAINTABLE"
			  + " WHERE ADV_PROJECT.ADV_PROJECT_ID=KPI_MAINTABLE.ADV_PROJECT_ID AND KPI_MAINTABLE.PRT_CAMPAIGN_ID=" + id;
	
		pst= con.prepareStatement(query);
	  
		rs = pst.executeQuery(query);
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
		
		return jArr.toString();
	}
	
	public static String getAdvCompaign(Connection con,String exchangeId, int idAdv,
			String from,String to) throws SQLException{
		
		jArr = new JsonArray();
		int id = Integer.parseInt(exchangeId);
		
		System.out.println("add campaign");
		
		query = "SELECT distinct ADV_CAMPAIGN.ADV_CAMPAIGN_ID , ADV_CAMPAIGN.ADV_CAMPAIGN_NAME"
			+" FROM ADV_CAMPAIGN , KPI_MAINTABLE"
			+" WHERE ADV_CAMPAIGN.ADV_CAMPAIGN_ID=KPI_MAINTABLE.ADV_CAMPAIGN_ID"
			+" AND KPI_MAINTABLE.PRT_CAMPAIGN_ID="+id
			+" AND KPI_MAINTABLE.ADV_PROJECT_ID="+idAdv;

		st = con.createStatement();
		rs = st.executeQuery(query);
		
		while(rs.next()){
			jObj = new JsonObject();
		//	jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
		//	jObj.add("children",getAdGroup(con,exchangeId,noBidId,idAdv, rs.getInt(1)));
			jArr.add(jObj);
		}
	
	  return jArr.toString();
	}
	
}
