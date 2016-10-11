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
	private static JsonArray jArr;
	private static JsonObject jObj;
	private static String query;
	
	private KpiQuery(){}
	
	
	public static String getDates(Connection con) throws SQLException{
		
		jArr = new JsonArray();
		
		query = "SELECT * FROM KPI_Dates;";
		
		st =  con.createStatement();
		rs = st.executeQuery(query);
		
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("date", rs.getDate(1).toString());
			jArr.add(jObj);
		}
        return jArr.toString();
	}
	
	
	public static String getExchanges(Connection con) throws SQLException{
		
		jArr = new JsonArray();
		
//		query = "SELECT * FROM PRT_CAMPAIGN;";
		query = "SELECT * FROM Exchanges;";
		
		st =  con.createStatement();
		rs = st.executeQuery(query);
		
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
        return jArr.toString();
	}	
	
	
	public static String getAdvertisers(Connection con, String exchId, String fromDate, String toDate) throws SQLException{
		
		jArr = new JsonArray();
	
		/*query = "SELECT distinct ADV_PROJECT.ADV_PROJECT_ID , ADV_PROJECT.ADV_PROJECT_NAME"
			  + " FROM ADV_PROJECT , KPI_MAINTABLE"
			  + " WHERE ADV_PROJECT.ADV_PROJECT_ID=KPI_MAINTABLE.ADV_PROJECT_ID AND KPI_MAINTABLE.PRT_CAMPAIGN_ID=" + exchId;*/
		query = "SELECT DISTINCT Advertisers.advertiserID, Advertisers.advertiserName"
			  +	" FROM Advertisers INNER JOIN Exch_Adv"
			  + " WHERE Advertisers.advertiserID=Exch_Adv.advertiserID"
			  + " AND Exch_Adv.exchangeID=" + exchId
			  + " AND Exch_Adv.eventDate BETWEEN '"+ fromDate +"' AND '"+ toDate +"';";
	
		st = con.createStatement();
		rs = st.executeQuery(query);
		
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
		return jArr.toString();
	}

	
	public static String getCampaigns(Connection con, String exchId, String advId, String fromDate, String toDate) throws SQLException{
		
		jArr = new JsonArray();
		
		/*query = "SELECT distinct ADV_CAMPAIGN.ADV_CAMPAIGN_ID , ADV_CAMPAIGN.ADV_CAMPAIGN_NAME"
			  + " FROM ADV_CAMPAIGN , KPI_MAINTABLE"
			  + " WHERE ADV_CAMPAIGN.ADV_CAMPAIGN_ID=KPI_MAINTABLE.ADV_CAMPAIGN_ID"
			  + " AND KPI_MAINTABLE.PRT_CAMPAIGN_ID=" + exchId
			  + " AND KPI_MAINTABLE.ADV_PROJECT_ID=" + advId;*/
		query = "SELECT DISTINCT Campaigns.campaignID, Campaigns.campaignName"
			  +	" FROM Campaigns INNER JOIN Exch_Camp"
			  +	" WHERE Campaigns.campaignID=Exch_Camp.campaignID" 
			  +	" AND Campaigns.advertiserID=" + advId
			  +	" AND Exch_Camp.exchangeID=" + exchId
			  +	" AND Exch_Camp.eventDate BETWEEN '" + fromDate + "' AND '"+ toDate +"';";

		st = con.createStatement();
		rs = st.executeQuery(query);
		
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
		return jArr.toString();
	}
	
	
	public static String getGroups(Connection con, String exchId, String advId, String campId, String fromDate, String toDate) throws SQLException{
		
		jArr = new JsonArray();
		
		/*query = "SELECT distinct ADGROUP.ADGROUP_ID , ADGROUP.AD_GROUP_NAME"
			  + " FROM ADGROUP , KPI_MAINTABLE"
			  + " WHERE ADGROUP.ADGROUP_ID = KPI_MAINTABLE.ADGROUP_ID"
			  + " AND KPI_MAINTABLE.PRT_CAMPAIGN_ID=" + exchId
			  + " AND KPI_MAINTABLE.ADV_PROJECT_ID=" + advId
			  + " AND KPI_MAINTABLE.ADV_CAMPAIGN_ID=" + campId;*/
		query = "SELECT DISTINCT Groups.groupID, Groups.groupName"
			  + " FROM Groups INNER JOIN Exch_Group"
			  + " WHERE Groups.groupID=Exch_Group.groupID" 
			  +	" AND Exch_Group.exchangeID=" + exchId
			  +	" AND Groups.advertiserID=" + advId
			  +	" AND Groups.campaignID=" + campId
			  +	" AND Exch_Group.eventDate BETWEEN '" + fromDate + "' AND '"+ toDate +"';";

		st = con.createStatement();
		rs = st.executeQuery(query);
		
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
	
	    return jArr.toString();
	}
	
	
	public static String getCreatives(Connection con,String exchId, String advId, String campId, String groupId,String fromDate,String toDate) throws SQLException{
		
		jArr = new JsonArray();
		
		query = "SELECT DISTINCT KPI_MAINTABLE.ADV_PROJECT_CREATIVE_ID,ADV_PROJECT_CREATIVE.CREATIVE_NAME"
			  + " FROM KPI_MAINTABLE,ADV_PROJECT_CREATIVE"
			  + " WHERE KPI_MAINTABLE.PRT_CAMPAIGN_ID="+exchId
			  + " AND KPI_MAINTABLE.ADV_PROJECT_CREATIVE_ID=ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID"
			  + " AND KPI_MAINTABLE.ADV_PROJECT_ID="+advId
			  + " AND KPI_MAINTABLE.ADV_CAMPAIGN_ID="+campId
			  + " AND KPI_MAINTABLE.ADGROUP_ID="+groupId
			  + " AND KPI_MAINTABLE.EVENT_DATE BETWEEN '"+ fromDate +"' AND '"+ toDate +"'";

		st = con.createStatement();
		rs = st.executeQuery(query);
		
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
	
	    return jArr.toString();
	}
}
