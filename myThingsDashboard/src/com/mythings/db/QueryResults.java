package com.mythings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class QueryResults {
	
	private static Statement st;
	private static ResultSet rs;
	private static JsonArray jArr;
	private static JsonObject jObj;
	private static String query;
	private static PreparedStatement pst;
	
	private QueryResults(){}
	
	
	public static String getExchange(Connection con) throws SQLException{
		
		st =  con.createStatement();
		
		jArr = new JsonArray();
		rs = st.executeQuery("SELECT * FROM Updated.NOBID_PRT_CAMPAIGN;");
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
		
        return jArr.toString();
	}	
	
	
	public static String getNoBidReasons(Connection con,String exchangeId ) throws SQLException{
			
	    jArr = new JsonArray();
	    int id = Integer.parseInt(exchangeId);
	    
	    query = "SELECT distinct REASON_TYPE.NO_BID_REASON_TYPE_ID,REASON_TYPE.NO_BID_REASON_TYPE"
	       	  + " FROM REASON_TYPE inner join NOBID_MAINTABLE"
	       	  + " ON NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID=REASON_TYPE.NO_BID_REASON_TYPE_ID"
	       	  + " WHERE NOBID_MAINTABLE.PRT_CAMPAIGN_ID=" +id;
	    
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
	
	
	public static String getAdv(Connection con,String exchangeId,String... noBidId ) throws SQLException{
		
		System.out.println("start getAdv.....");
		jArr = new JsonArray();
		int id = Integer.parseInt(exchangeId);
	
		for(int i=0;i<noBidId.length;i++){
			query="SELECT DISTINCT ADV_PROJECT.ADV_PROJECT_ID,ADV_PROJECT.ADV_PROJECT_NAME FROM"
					+ " ADV_PROJECT,NOBID_MAINTABLE WHERE NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+id+" AND"
	  				+ " NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId[i]+" AND"
	  				+ " ADV_PROJECT.ADV_PROJECT_ID=NOBID_MAINTABLE.ADV_PROJECT_ID"
	  				+ " LIMIT 3";
	
		pst= con.prepareStatement(query);
		rs = st.executeQuery(query);
		while(rs.next()){
			jObj = new JsonObject();
			//jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jObj.add("children",getAdvCompaign(con,exchangeId,noBidId[i], rs.getInt(1)));
			jArr.add(jObj);
		}
		
		System.out.println("Finished getAdv");
	}
		return "{"+"\"name\":\"Global\",\"children\":" + jArr.toString() + "}";
	}	
	
	
	public static JsonArray getAdvCompaign(Connection con,String exchangeId,String noBidId, int idAdv) throws SQLException{
		
		jArr = new JsonArray();
		int id = Integer.parseInt(exchangeId);
		
		System.out.println("add compain");
		
		query = "select distinct ADV_CAMPAIGN.ADV_CAMPAIGN_ID,ADV_CAMPAIGN.ADV_CAMPAIGN_NAME"
			+" from ADV_PROJECT,NOBID_MAINTABLE,ADV_CAMPAIGN"
			+" where NOBID_MAINTABLE.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID and"
			+" NOBID_MAINTABLE.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID"
			+" and NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+id+" AND NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId
			+" and ADV_PROJECT.ADV_PROJECT_ID="+idAdv
			+ " LIMIT 4";

		pst= con.prepareStatement(query);
		rs = st.executeQuery(query);
		while(rs.next()){
			jObj = new JsonObject();
		//	jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jObj.add("children",getAdGroup(con,exchangeId,noBidId,idAdv, rs.getInt(1)));
			jArr.add(jObj);
		}
	
	  return jArr;
	}
	

	public static JsonArray getAdGroup(Connection con,String exchangeId,String noBidId, int idAdv,int idCompaign) throws SQLException{
		
		System.out.println("add adgroup for "+idCompaign);

		jArr = new JsonArray();
		int id = Integer.parseInt(exchangeId);
		 
		query= "select distinct ADGROUP.ADGROUP_ID ,ADGROUP.AD_GROUP_NAME"
			+ " from ADV_PROJECT,NOBID_MAINTABLE,ADV_CAMPAIGN,ADGROUP"
			+ " where NOBID_MAINTABLE.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID"
			+ " and NOBID_MAINTABLE.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID"
			+ " and NOBID_MAINTABLE.ADGROUP_ID=ADGROUP.ADGROUP_ID"
			+ " and NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+id+" and NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId
			+ " and ADV_PROJECT.ADV_PROJECT_ID="+idAdv+" and ADV_CAMPAIGN.ADV_CAMPAIGN_ID="+idCompaign
			+ " LIMIT 5";

		 
		 pst= con.prepareStatement(query);
		 rs = pst.executeQuery(query);
		 while(rs.next()){
			jObj = new JsonObject();
			//jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jObj.add("children",getCreatives(con,exchangeId,noBidId,idAdv, idCompaign, rs.getInt(1)));
			jArr.add(jObj);
		}
	
		 return jArr;
	}
	

	public static JsonArray  getCreatives(Connection con,String exchangeId,String noBidId, int advId,int idCampaign,int adGroup) throws SQLException{
		
		 Statement st =  con.createStatement();
		 jArr = new JsonArray();
		 
		 System.out.println("getCreatives Started");
		 System.out.println("add Creative for "+adGroup);

		 query="select distinct ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID, ADV_PROJECT_CREATIVE.CREATIVE_NAME"
					+" from ADV_PROJECT,NOBID_MAINTABLE,ADV_CAMPAIGN,ADGROUP,ADV_PROJECT_CREATIVE"
					+" where NOBID_MAINTABLE.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID "
					+" and NOBID_MAINTABLE.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID and"
					+" NOBID_MAINTABLE.ADGROUP_ID=ADGROUP.ADGROUP_ID and NOBID_MAINTABLE.ADV_PROJECT_CREATIVE_ID=ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID"
					+" and NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+exchangeId
					+" and NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId
					+" and ADV_PROJECT.ADV_PROJECT_ID="+advId
					+" and ADV_CAMPAIGN.ADV_CAMPAIGN_ID="+idCampaign
					+" and ADGROUP.ADGROUP_ID="+adGroup;

		rs = st.executeQuery(query);
		while(rs.next()){
			jObj=new JsonObject();
		//	jObj.addProperty("Creative Name", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);	
		}
		 System.out.println("getCreatives Finished");
		 return jArr;
	}	
	
	
	public static String getExample(Connection con,String exchangeId,String... noBidId ) throws SQLException{
		
		jArr = new JsonArray();
		int id = Integer.parseInt(exchangeId);
	 
	
		for(int i=0;i<noBidId.length;i++)
			query="SELECT ADV_PROJECT.ADV_PROJECT_NAME , sum(NOBID_MAINTABLE.CNT)"
			+" FROM ADV_PROJECT,NOBID_MAINTABLE"
			+" WHERE NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+id+" AND NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId[i]
			+" and ADV_PROJECT.ADV_PROJECT_ID = NOBID_MAINTABLE.ADV_PROJECT_ID"
			+" group by ADV_PROJECT.ADV_PROJECT_NAME";
	
		pst= con.prepareStatement(query);
		
		rs = pst.executeQuery(query);
		while(rs.next()){
			jObj = new JsonObject();
			//jObj.addProperty("Advertiser", rs.getString(1));
		 	jObj.addProperty("Clicks", rs.getInt(2));
	
			jArr.add(jObj);
		   }
	     
		return jArr.toString();
	}	

}