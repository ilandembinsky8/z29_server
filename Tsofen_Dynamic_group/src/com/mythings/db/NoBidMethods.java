package com.mythings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class NoBidMethods {

	
	
	private NoBidMethods(){}
	
	
	public static String getExchange(Connection con) throws SQLException{
		
		Statement st =  con.createStatement();
		 ResultSet rs;
		 JsonArray jArr;
		 JsonObject jObj;
		
		
       jArr = new JsonArray();
       System.out.println("Exhange Started");
		rs = st.executeQuery("SELECT * FROM Updated.NOBID_PRT_CAMPAIGN;");
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
		System.out.println("Exhange Finished");
	
        return jArr.toString();
	}	
	
public static String getNoBidReason(Connection con,String exchangeid ) throws SQLException{
		
		 ResultSet rs;
		 JsonArray jArr;
		 JsonObject jObj;
       jArr = new JsonArray();
       int id = Integer.parseInt(exchangeid);
       String query="select distinct REASON_TYPE.NO_BID_REASON_TYPE_ID , "
       		+ "REASON_TYPE.NO_BID_REASON_TYPE from REASON_TYPE inner join NOBID_MAINTABLE"
       		+ " on NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID=REASON_TYPE.NO_BID_REASON_TYPE_ID "
       		+ "where NOBID_MAINTABLE.PRT_CAMPAIGN_ID=" +id;
       
       PreparedStatement st= con.prepareStatement(query);
 
       System.out.println("NoBid Started");
		rs = st.executeQuery(query);
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
	
		
		 System.out.println("NoBid Finished");
        return jArr.toString();
	}

public static String getAdv(Connection con,String exchangeId,String... noBidId ) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 String query="";

	 jArr = new JsonArray();
  //int id = Integer.parseInt(exchangeId);
  System.out.println("getAdv Started");
for(int i=0;i<noBidId.length;i++){
  query="SELECT DISTINCT ADV_PROJECT.ADV_PROJECT_ID,ADV_PROJECT.ADV_PROJECT_NAME FROM "
  		+ "ADV_PROJECT,NOBID_MAINTABLE WHERE NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+exchangeId+" AND "
  				+ "NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId[i]+" AND "
  						+ "ADV_PROJECT.ADV_PROJECT_ID=NOBID_MAINTABLE.ADV_PROJECT_ID";

  PreparedStatement st= con.prepareStatement(query);
  
	rs = st.executeQuery(query);
	while(rs.next()){
		jObj = new JsonObject();
		jObj.addProperty("id", rs.getInt(1));
		jObj.addProperty("name", rs.getString(2));
		jArr.add(jObj);
	}
}
System.out.println("getAdv Finished");
   return jArr.toString();
}	

public static String getAdvCampaign(Connection con,String exchangeId,String noBidId, String advertiserid) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 String query="";

 jArr = new JsonArray();
 int id = Integer.parseInt(exchangeId);
 int idAdv=Integer.parseInt(advertiserid);
 
 System.out.println(" getAdvCampaign Started");
 
 query="select distinct ADV_CAMPAIGN.ADV_CAMPAIGN_ID,ADV_CAMPAIGN.ADV_CAMPAIGN_NAME"+""
		+" from ADV_PROJECT,NOBID_MAINTABLE,ADV_CAMPAIGN "
		+" where NOBID_MAINTABLE.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID and "
		+"NOBID_MAINTABLE.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID "
		+"and NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+id+" AND NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId+
		" and ADV_PROJECT.ADV_PROJECT_ID="+idAdv;

 PreparedStatement st= con.prepareStatement(query);
 
	rs = st.executeQuery(query);
	while(rs.next()){
		jObj = new JsonObject();
		jObj.addProperty("id", rs.getInt(1));
		jObj.addProperty("name", rs.getString(2));
		jArr.add(jObj);
	}

	 System.out.println(" getAdvCampaign Finished");
  return jArr.toString();
}
public static String getAdGroup(Connection con,String exchangeId,String noBidId, String advertiserid,String idCampaign) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 String query="";
jArr = new JsonArray();
int id = Integer.parseInt(exchangeId);
int idAdv=Integer.parseInt(advertiserid);
int idCamp=Integer.parseInt(idCampaign);
System.out.println(" getAdGroup Started");
query= "select distinct ADGROUP.ADGROUP_ID ,ADGROUP.AD_GROUP_NAME"
		+ " from ADV_PROJECT,NOBID_MAINTABLE,ADV_CAMPAIGN,ADGROUP"
		+ " where NOBID_MAINTABLE.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID"
		+" and NOBID_MAINTABLE.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID"
		+" and NOBID_MAINTABLE.ADGROUP_ID=ADGROUP.ADGROUP_ID"
		+" and NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+id+" and NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId
		+" and ADV_PROJECT.ADV_PROJECT_ID="+idAdv+" and ADV_CAMPAIGN.ADV_CAMPAIGN_ID="+idCamp;
PreparedStatement st= con.prepareStatement(query);
	rs = st.executeQuery(query);
	while(rs.next()){
		jObj = new JsonObject();
		jObj.addProperty("id", rs.getInt(1));
		jObj.addProperty("name", rs.getString(2));
		jArr.add(jObj);
	}

	System.out.println(" getAdGroup Finished");
 return jArr.toString();
}

public static String  getCreatives(Connection con,String exchangeId,String noBidId, String advId,String idCampaign,String adGroup) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 Statement st =  con.createStatement();
	 jArr = new JsonArray();
	 System.out.println("getCreatives Started");
	String query="select distinct ADV_PROJECT_CREATIVE.CREATIVE_NAME,ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID"+
"from ADV_PROJECT,NOBID_MAINTABLE,ADV_CAMPAIGN,ADGROUP,ADV_PROJECT_CREATIVE"+
"where NOBID_MAINTABLE.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID and NOBID_MAINTABLE.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID and"+
"NOBID_MAINTABLE.ADGROUP_ID=ADGROUP.ADGROUP_ID and NOBID_MAINTABLE.ADV_PROJECT_CREATIVE_ID=ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID"+
"and NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+exchangeId+" and NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId+" and ADV_PROJECT.ADV_PROJECT_ID="+advId+" and ADV_CAMPAIGN.ADV_CAMPAIGN_ID="+idCampaign+" and ADGROUP.ADGROUP_ID="+adGroup+"";
	rs = st.executeQuery(query);
	while(rs.next()){
		jObj=new JsonObject();
		jObj.addProperty("Creative Name", rs.getInt(1));
		jObj.addProperty("Adv Id", rs.getString(2));
		jArr.add(jObj);
		
		
	}
	 System.out.println("getCreatives Finished");
	 return jArr.toString();
}



public static String  getGraph(Connection con,String exchangeId,String noBidId, String advId,String idCampaign,String adGroup,String creativeId) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 Statement st =  con.createStatement();
	 jArr = new JsonArray();
	 System.out.println("getCreatives Started");
	String query="SELECT NOBID_MAINTABLE.EVENT_HOUR, SUM(NOBID_MAINTABLE.CNT) AS COUNTER"+
"FROM ADV_PROJECT,NOBID_MAINTABLE,ADV_CAMPAIGN,ADGROUP,ADV_PROJECT_CREATIVE"+
"WHERE  NOBID_MAINTABLE.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID and"+
 "NOBID_MAINTABLE.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID "+
 "and NOBID_MAINTABLE.ADGROUP_ID=ADGROUP.ADGROUP_ID "+
" and NOBID_MAINTABLE.ADV_PROJECT_CREATIVE_ID=ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID"+
"and NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+exchangeId+" and NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId+""+ 
"and ADV_PROJECT.ADV_PROJECT_ID="+advId+" and ADV_CAMPAIGN.ADV_CAMPAIGN_ID="+idCampaign+" and "+
"ADGROUP.ADGROUP_ID="+adGroup+" AND NOBID_MAINTABLE.ADV_PROJECT_CREATIVE_ID="+creativeId+"GROUP BY EVENT_HOUR" ;

	rs = st.executeQuery(query);
	while(rs.next()){
		jObj=new JsonObject();
		jObj.addProperty("Creative Name", rs.getInt(1));
		jObj.addProperty("Adv Id", rs.getString(2));
		jArr.add(jObj);
		
		
	}
	 System.out.println("getCreatives Finished");
	 return jArr.toString();
}



//Example
public static String getExample(Connection con,String exchangeId,String... noBidId ) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 String query="";
 jArr = new JsonArray();
 int id = Integer.parseInt(exchangeId);
 
 System.out.println("Graph Started");
 for(int i=0;i<noBidId.length;i++)
 {
 query="SELECT ADV_PROJECT.ADV_PROJECT_NAME , sum(NOBID_MAINTABLE.CNT)"
		+" FROM ADV_PROJECT,NOBID_MAINTABLE"
		+" WHERE NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+id+" AND NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId[i]
		+" and ADV_PROJECT.ADV_PROJECT_ID = NOBID_MAINTABLE.ADV_PROJECT_ID"
		+" group by ADV_PROJECT.ADV_PROJECT_NAME";

 PreparedStatement st= con.prepareStatement(query);
 
    
	rs = st.executeQuery(query);
	while(rs.next()){
		jObj = new JsonObject();
		jObj.addProperty("Event_Hour", rs.getString(1));
	 	jObj.addProperty("Counter", rs.getInt(2));

		jArr.add(jObj);
	   }
     }
	  
	    System.out.println("Graph Finished");
	
	
       return jArr.toString();
    }	

}
