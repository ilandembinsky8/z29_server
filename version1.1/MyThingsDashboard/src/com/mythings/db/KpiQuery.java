package com.mythings.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class KpiQuery {
	
	private static Statement st;
	private static ResultSet rs;
	private static String query;
	private static JsonArray jArr;
	private static JsonObject jObj;
	
	
	private KpiQuery(){ super(); }

	
	private static void runQuery(Connection con) throws SQLException{
		st = con.createStatement();
		rs = st.executeQuery(query);
	}
	
	
	private static String prepareJsonArray() throws SQLException{
		
		jArr = new JsonArray();
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
		
        return jArr.toString();
	}
	
	
	public static String getDates(Connection con) throws SQLException{
		
		jArr = new JsonArray();
		st = con.createStatement();
		st.execute("USE KPI;");
		query = "SELECT * FROM Dates;";
		runQuery(con);
		
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("date", rs.getDate(1).toString());
			jArr.add(jObj);
		}
	
        return jArr.toString();
	}
	
	
	public static String getExchanges(Connection con) throws SQLException{
		
		query = "SELECT * FROM Exchanges;";
		runQuery(con);
		return prepareJsonArray();
	}	
	
	
	public static String getAdvertisers(Connection con, String exchId, String fromDate, String toDate) throws SQLException{
	
		query = "SELECT DISTINCT Advertisers.advertiserID, Advertisers.advertiserName"
			  +	" FROM Advertisers INNER JOIN Exch_Adv"
			  + " WHERE Advertisers.advertiserID=Exch_Adv.advertiserID"
			  + " AND Exch_Adv.exchangeID=" + exchId
			  + " AND Exch_Adv.eventDate BETWEEN '"+ fromDate +"' AND '"+ toDate +"';";
	
		runQuery(con);
		return prepareJsonArray();
	}

	
	public static String getCampaigns(Connection con, String exchId, String advId, String fromDate, String toDate) throws SQLException{
		
		query = "SELECT DISTINCT Campaigns.campaignID, Campaigns.campaignName"
			  +	" FROM Campaigns INNER JOIN Exch_Camp"
			  +	" WHERE Campaigns.campaignID=Exch_Camp.campaignID" 
			  +	" AND Campaigns.advertiserID=" + advId
			  +	" AND Exch_Camp.exchangeID=" + exchId
			  +	" AND Exch_Camp.eventDate BETWEEN '" + fromDate + "' AND '"+ toDate +"';";

		runQuery(con);
		return prepareJsonArray();
	}
	
	
	public static String getGroups(Connection con, String exchId, String advId, String campId, String fromDate, String toDate) throws SQLException{
		
		query = "SELECT DISTINCT Groups.groupID, Groups.groupName"
			  + " FROM Groups INNER JOIN Exch_Group"
			  + " WHERE Groups.groupID=Exch_Group.groupID" 
			  +	" AND Exch_Group.exchangeID=" + exchId
			  +	" AND Groups.advertiserID=" + advId
			  +	" AND Groups.campaignID=" + campId
			  +	" AND Exch_Group.eventDate BETWEEN '" + fromDate + "' AND '"+ toDate +"';";

		runQuery(con);
		return prepareJsonArray();
	}
	
	
	public static String getCreatives(Connection con,String exchId, String advId, String campId, String groupId,String fromDate,String toDate) throws SQLException{
		
		query = "SELECT DISTINCT Creatives.creativeID, Creatives.creativeName"
			  + " FROM Creatives,Exch_Creative,Camp_Creative,Group_Creative"
			  + " WHERE Creatives.creativeID=Exch_Creative.creativeID"
			  + " AND Creatives.creativeID=Camp_Creative.creativeID"
			  + " AND Exch_Creative.exchangeID="+exchId
			  + " AND Creatives.advertiserId="+advId
			  + " AND Camp_Creative.campaignID="+campId
			  + " AND Group_Creative.groupID="+groupId
			  + " AND Exch_Creative.eventDate BETWEEN '"+ fromDate +"' AND '"+ toDate +"'";

		runQuery(con);
		return prepareJsonArray();
	}


	public static String getGraphJson(Connection con,String exchId, String advId, String campId, String groupId, String creativeId, String fromDate,String toDate) throws SQLException{
		
		jArr = new JsonArray();
	
		query = "SELECT eventDate,SUM(impressions) AS impressions,SUM(clicks) AS clicks"
			  + " FROM MAINTABLE"
			  + " WHERE exchangeID="+exchId
			  + " AND advertiserID="+advId
			  + " AND campaignID="+campId
			  + " AND groupID="+groupId
			  + " AND creativeID="+creativeId
			  + " AND eventDate BETWEEN '"+ fromDate +"' AND '"+ toDate +"'"
			  + " GROUP BY eventDate;";

		runQuery(con);
		
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("date", rs.getDate(1).toString());
			jObj.addProperty("impressions", rs.getInt(2));
			jObj.addProperty("clicks", rs.getInt(3));
			jArr.add(jObj);
		}
		
	    return jArr.toString();
	}

	
	public static void close_RS_ST() throws SQLException{
		st.close();
		rs.close();
	}
}
