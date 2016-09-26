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
		//con.close();
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
		//con.close();
		
		 System.out.println("NoBid Finished");
        return jArr.toString();
	}

public static String getAdv(Connection con,String exchangeId,String... noBidId ) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 String query="";
//System.out.println(ExchangeId);
  jArr = new JsonArray();
  int id = Integer.parseInt(exchangeId);

for(int i=0;i<noBidId.length;i++){
  query="SELECT DISTINCT ADV_PROJECT.ADV_PROJECT_ID,ADV_PROJECT.ADV_PROJECT_NAME FROM "
  		+ "ADV_PROJECT,NOBID_MAINTABLE WHERE NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+id+" AND "
  				+ "NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId[i]+" AND "
  						+ "ADV_PROJECT.ADV_PROJECT_ID=NOBID_MAINTABLE.ADV_PROJECT_ID";

  PreparedStatement st= con.prepareStatement(query);
  
	rs = st.executeQuery(query);
	while(rs.next()){
		jObj = new JsonObject();
//		jObj.addProperty("id", rs.getInt(1));
		jObj.addProperty("name", rs.getString(2));
		jArr.add(jObj);
	}
}
	//con.close();
	System.out.println(jArr.toString());
   return "{"+"\"name\":\"Global\",\"children\":" + jArr.toString() + "}";
}	

public static String getAdvCompaign(Connection con,String exchangeId,String noBidId, String advertiserid) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 String query="";
//System.out.println(ExchangeId);
 jArr = new JsonArray();
 int id = Integer.parseInt(exchangeId);
 int idAdv=Integer.parseInt(advertiserid);
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

	//con.close();
	System.out.println(jArr.toString());
  return jArr.toString();
}
public static String getAdGroup(Connection con,String exchangeId,String noBidId, String advertiserid,String id_compaign) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 String query="";
//System.out.println(ExchangeId);
jArr = new JsonArray();
int id = Integer.parseInt(exchangeId);
int idAdv=Integer.parseInt(advertiserid);
int idCompaign=Integer.parseInt(id_compaign);
query= "select distinct ADGROUP.ADGROUP_ID ,ADGROUP.AD_GROUP_NAME"
		+ " from ADV_PROJECT,NOBID_MAINTABLE,ADV_CAMPAIGN,ADGROUP"
		+ " where NOBID_MAINTABLE.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID"
		+" and NOBID_MAINTABLE.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID"
		+" and NOBID_MAINTABLE.ADGROUP_ID=ADGROUP.ADGROUP_ID"
		+" and NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+id+" and NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId
		+" and ADV_PROJECT.ADV_PROJECT_ID="+idAdv+" and ADV_CAMPAIGN.ADV_CAMPAIGN_ID="+idCompaign;
PreparedStatement st= con.prepareStatement(query);
	rs = st.executeQuery(query);
	while(rs.next()){
		jObj = new JsonObject();
		jObj.addProperty("id", rs.getInt(1));
		jObj.addProperty("name", rs.getString(2));
		jArr.add(jObj);
	}

	//con.close();
	System.out.println(jArr.toString());
 return jArr.toString();
}
/*public static String getTree(Connection con,String exchangeId,String noBidId ) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 String query="";
jArr = new JsonArray();
int id = Integer.parseInt(exchangeId);




		jObj = new JsonObject();
		jObj.addProperty("Advertiser", rs.getString(1));
	 	jObj.addProperty("Clicks", rs.getInt(2));

		jArr.add(jObj);
	   }
   

	   // con.close();	
      return jArr.toString();
   }	*/

//Example
public static String getExample(Connection con,String exchangeId,String... noBidId ) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 String query="";
 jArr = new JsonArray();
 int id = Integer.parseInt(exchangeId);
 

 for(int i=0;i<noBidId.length;i++)
 {
 query="SELECT ADV_PROJECT.ADV_PROJECT_NAME , sum(NOBID_MAINTABLE.CNT)"
		+" FROM ADV_PROJECT,NOBID_MAINTABLE"
		+" WHERE NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+id+" AND NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId[i]
		+" and ADV_PROJECT.ADV_PROJECT_ID = NOBID_MAINTABLE.ADV_PROJECT_ID"
		+" group by ADV_PROJECT.ADV_PROJECT_NAME";

 PreparedStatement st= con.prepareStatement(query);
 
    System.out.println("Graph Started");
	rs = st.executeQuery(query);
	while(rs.next()){
		jObj = new JsonObject();
		jObj.addProperty("Advertiser", rs.getString(1));
	 	jObj.addProperty("Clicks", rs.getInt(2));

		jArr.add(jObj);
	   }
     }
	   // con.close();
	    System.out.println("Graph Finished");
	  System.out.println(jArr.toString());
	
       return jArr.toString();
    }	

}
