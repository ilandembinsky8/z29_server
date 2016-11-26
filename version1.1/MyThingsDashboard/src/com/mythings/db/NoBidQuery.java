package com.mythings.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class NoBidQuery {

	private static Statement st;
	private static String query;
	private static String[] reasonsArr;
	
	private NoBidQuery() {}

	public static JsonArray getExchange(Connection con) throws SQLException {
		
		st = con.createStatement();
		st.execute("USE NoBidReasons;");
		ResultSet rs = st.executeQuery("SELECT * FROM Exchanges;");
		JsonArray jArr = new JsonArray();
		JsonObject jObj;
		
		while (rs.next()) {
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}

		return jArr;
	}

	public static JsonArray getReasons(Connection con, String exchangeId, String refDate, String refHour, String compDate, String compHour)throws SQLException {

		JsonArray jArr = new JsonArray();
		JsonObject jObj;
		
		st = con.createStatement();
		st.execute("DROP TABLE IF EXISTS temp;");
		query = "CREATE TABLE temp" 
			  + "("
			  + " SELECT *" 
			  + " FROM MAINTABLE"
			  + " WHERE (eventDate='"+refDate+"' OR eventDate='"+compDate+"')"
			  + " AND (eventHour="+refHour+" OR eventHour="+compHour+")"
			  + " AND exchangeID="+exchangeId+");";	
		st.execute(query);
		
		query = "SELECT reasonID,reasonName"
			  + " FROM Reasons"
			  + " WHERE reasonID"
			  + " IN" 
			  + "("
			  + " SELECT reasonID"
			  + " FROM Exch_Reason"
			  + " WHERE exchangeID="+exchangeId
			  + " AND eventDate='"+refDate+"'"
			  + " AND Exch_Reason.eventHour="+refHour
			  + ")"
			  + " AND reasonID"
			  + " IN"
			  + "("
			  + " SELECT reasonID"
			  + " FROM Exch_Reason"
			  + " WHERE exchangeID="+exchangeId
			  + " AND eventDate='"+compDate+"'"
			  + " AND Exch_Reason.eventHour="+compHour
			  + ");";

		ResultSet rs = st.executeQuery(query);
		
		while (rs.next()) {
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}

		return jArr;
	}

	public static JsonArray getAdv(Connection con, String refDate, String refHour, String compHour, String compDate, String reasons) throws SQLException {
	
		JsonArray jArr = new JsonArray();
		JsonObject jObj;
		reasonsArr = reasons.toString().split(",");
		
		st = con.createStatement();
		st.execute("DROP TABLE IF EXISTS temp2;");
		
		query = "CREATE TABLE temp2"
			  + "(SELECT * FROM temp WHERE (reasonID="+reasonsArr[0];
		
		for(int i=1; i<reasonsArr.length ;i++)
			query += " OR reasonID="+reasonsArr[i];
		
		query += "));";
	    st.execute(query);
	    
	    query = "CALL prepare_temp2();";
	    CallableStatement cs = con.prepareCall(query);
	    cs.execute();
	    
	    query = "ALTER TABLE temp2 DROP COLUMN exchangeID;";
	    st.execute(query);
		
		query =	"SELECT advertiserID,advertiserName" 
			  + " FROM Advertisers"
			  + " WHERE advertiserID"
			  + " IN"
			  + "("
			  + " SELECT DISTINCT advertiserID"
		      + " FROM temp2"
			  + " WHERE eventDate='"+refDate+"'"
			  + " AND eventHour="+refHour
			  + ")"
			  + " AND advertiserID"
			  + " IN"
			  + "("
			  + " SELECT DISTINCT advertiserID"
			  + " FROM temp2"
	          + " WHERE eventDate='"+compDate+"'"
	          + " AND eventHour="+compHour
	          + ");";
		
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("_id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jObj.addProperty("clickedBefore", false);
			jArr.add(jObj);
		}
			
		return jArr;
	}

	public static JsonObject getAdvTree(Connection con, String refDate, String refHour, String compHour, String compDate, String reasons, int advId,String advName) throws SQLException {
		
		JsonArray campaigns;
		JsonObject root = new JsonObject();
		reasonsArr = reasons.toString().split(",");
		campaigns = getCampaign(con,refDate,refHour,compHour,compDate,advId);
		root.addProperty("_id", advId);
		root.addProperty("name", advName+" <"+campaigns.size()+">");
		root.add("children", campaigns);
		return root;
	}
	
	public static JsonArray getCampaign(Connection con, String refDate,String refHour,String compHour,String compDate,int advId) throws SQLException {

		JsonArray jArr = new JsonArray();
		JsonObject jObj;
		
		query = "SELECT campaignID,campaignName"
			  + " FROM Campaigns"
			  + " WHERE campaignID "
			  + " IN"
			  + "("
			  + " SELECT campaignID"
			  + " FROM temp2"
              + " WHERE eventDate='"+refDate+"'"
              + " AND eventHour="+refHour
              + ")"
              + " AND campaignID"
              + " IN"
              + "("
              + " SELECT campaignID"
			  + " FROM temp2"
			  + " WHERE eventDate='"+compDate+"'"
              + " AND eventHour="+compHour
              + ")"
              + " AND advertiserID="+advId;
		
		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()){
			JsonArray groups = getGroup(con,refDate,refHour,compHour,compDate,advId,rs.getInt(1));
			jObj = new JsonObject();
			jObj.addProperty("name", rs.getString(2)+" <"+groups.size()+">");
			jObj.add("children", groups);
			jArr.add(jObj);
		}
		
		return jArr;
	}

	private static JsonArray getGroup(Connection con,String refDate,String refHour,String compHour,String compDate,int advId,int campId) throws SQLException {

		JsonArray jArr = new JsonArray();
		JsonObject jObj;
		
		query = "SELECT groupID,groupName"
			  +	" FROM Groups"
			  + " WHERE groupID"
			  + " IN"
			  + "("
			  + " SELECT groupID"
			  + " FROM temp2"
			  + " WHERE eventDate='"+refDate+"'"
              + " AND eventHour="+refHour
              + ")"
              + " AND groupID"
              + " IN"
              + "("
              + " SELECT groupID"
			  + " FROM temp2"
              + " WHERE eventDate='"+compDate+"'"
              + " AND eventHour="+compHour
              + ")"
              + " AND advertiserID="+advId
              + " AND campaignID="+campId;
		
		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()){
			JsonArray creatives = getCreatives(con,refDate,refHour,compHour,compDate,advId,campId,rs.getInt(1));
			jObj = new JsonObject();
			jObj.addProperty("name", rs.getString(2)+" <"+creatives.size()+">");
			jObj.add("children", creatives);
			jArr.add(jObj);
		}
		
		return jArr;
	}

	private static JsonArray getCreatives(Connection con,String refDate,String refHour,String compHour,String compDate,int advId,int campId,int groupId) throws SQLException {

		JsonArray jArr = new JsonArray();
		JsonObject jObj;
		
		query = "SELECT creativeID,creativeName"
			  + " FROM Creatives"
			  + " WHERE creativeID"
			  + " IN"
			  + "("
			  + " SELECT creativeID"
			  + " FROM temp2"
			  + " WHERE eventDate='"+refDate+"'"
			  + " AND eventHour="+refHour
			  + " AND campaignID="+campId
			  + ")"
			  + " AND creativeID"
			  + " IN"
			  + "("
			  + " SELECT creativeID"
			  + " FROM temp2"
			  + " WHERE eventDate='"+compDate+"'"
			  + " AND eventHour="+compHour
			  + " AND campaignID="+campId
			  + ")"
			  + " AND advertiserID="+advId;
		
		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("name", String.format("%s <%.2f%%>",rs.getString(2),getDiffPercentage(con,advId,campId,groupId,rs.getInt(1))));
			jArr.add(jObj);
		}
		
		return jArr;
	}
	
	private static float getDiffPercentage(Connection con,int advId,int campId,int groupId,int creativeId) throws SQLException{
		
		int[] cntArr = new int[2];
		
		query = "SELECT DISTINCT eventDate,eventHour,SUM(cnt) AS cnt"
			  + " FROM temp2"
			  + " WHERE advertiserID="+advId
			  + " AND campaignID="+campId
			  + " AND groupID="+groupId
			  + " AND creativeID="+creativeId
			  + " GROUP BY eventDate,eventHour;";
		
		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		for(int i=0;rs.next();i++)
			cntArr[i] = rs.getInt(3);
		
		return (float)((cntArr[1]-cntArr[0])/((cntArr[1]+cntArr[0])/2.0)*100);
	}

	public static JsonObject prepareTree(Connection con, String refDate, String refHour, String compHour, String compDate, String reasons) throws SQLException{
		
		reasonsArr = reasons.toString().split(",");
		JsonArray advArr = getAdv(con,refDate,refHour,compHour,compDate,reasons);
		return getTree(con,advArr,refDate,refHour,compHour,compDate);
	} 
	
	private static int getCampCount(Connection con, String refDate,String refHour,String compHour,String compDate,String[] reasonsArr,int advId) throws SQLException {
		
		query = "SELECT COUNT(DISTINCT campaignID) as cnt"
			  + " FROM temp2"
			  + " WHERE campaignID "
			  + " IN"
			  + "("
			  + " SELECT campaignID"
			  + " FROM temp2"
              + " WHERE eventDate='"+refDate+"'"
              + " AND eventHour="+refHour
              + ")"
              + " AND campaignID"
              + " IN"
              + "("
              + " SELECT campaignID"
			  + " FROM temp2"
			  + " WHERE eventDate='"+compDate+"'"
              + " AND eventHour="+compHour
              + ")"
              + " AND advertiserID="+advId;
		
		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		if(rs.next())
			return rs.getInt(1);
		return 0;
	}
	
	public static JsonObject getTree(Connection con,JsonArray advArr, String refDate, String refHour, String compHour, String compDate) throws SQLException{
		
		JsonObject root = new JsonObject();
		int campCnt;
		root.addProperty("name", "Global <"+advArr.size()+">");
	
		for(int i=0; i<advArr.size(); i++){
			campCnt = getCampCount(con, refDate, refHour, compHour, compDate, reasonsArr, advArr.get(i).getAsJsonObject().get("_id").getAsInt());
			if(campCnt == 0) advArr.remove(i);
			else advArr.get(i).getAsJsonObject().addProperty("name", advArr.get(i).getAsJsonObject().get("name").getAsString()+" <"+campCnt+">");
			
			//JsonArray campaigns = getCampaign(con,refDate,refHour,compHour,compDate,reasonsArr,advArr.get(i).getAsJsonObject().get("_id").getAsInt());
			//advArr.get(i).getAsJsonObject().add("children", campaigns);
		}
		root.add("children", advArr);
		return root;
	}
}
