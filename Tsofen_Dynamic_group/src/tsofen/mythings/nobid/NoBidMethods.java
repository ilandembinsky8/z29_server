package tsofen.mythings.nobid;

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
	
public static String getNoBidReason(Connection con,String Exchangeid ) throws SQLException{
		
		 ResultSet rs;
		 JsonArray jArr;
		 JsonObject jObj;
//	System.out.println(Exchangeid);
       jArr = new JsonArray();
       int id = Integer.parseInt(Exchangeid);
       String query="select distinct REASON_TYPE.NO_BID_REASON_TYPE_ID , REASON_TYPE.NO_BID_REASON_TYPE from REASON_TYPE inner join NOBID_MAINTABLE on NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID=REASON_TYPE.NO_BID_REASON_TYPE_ID where NOBID_MAINTABLE.PRT_CAMPAIGN_ID=" +id;
       
       PreparedStatement st= con.prepareStatement(query);
    // st.setInt(1, id);
       
		rs = st.executeQuery(query);
		while(rs.next()){
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}
		con.close();
		System.out.println(jArr.toString());
        return jArr.toString();
	}



public static String getAdv(Connection con,String ExchangeId,String... noBidId ) throws SQLException{
	
	 ResultSet rs;
	 JsonArray jArr;
	 JsonObject jObj;
	 String query="";
//System.out.println(ExchangeId);
  jArr = new JsonArray();
  int id = Integer.parseInt(ExchangeId);

for(int i=0;i<noBidId.length;i++){
  query+="SELECT DISTINCT ADV_PROJECT.ADV_PROJECT_ID,ADV_PROJECT.ADV_PROJECT_NAME FROM ADV_PROJECT,NOBID_MAINTABLE WHERE NOBID_MAINTABLE.PRT_CAMPAIGN_ID="+id+" AND NOBID_MAINTABLE.NO_BID_REASON_TYPE_ID="+noBidId[i]+" AND ADV_PROJECT.ADV_PROJECT_ID=NOBID_MAINTABLE.ADV_PROJECT_ID";

  PreparedStatement st= con.prepareStatement(query);
// st.setInt(1, id);
  
	rs = st.executeQuery(query);
	while(rs.next()){
		jObj = new JsonObject();
		jObj.addProperty("id", rs.getInt(1));
		jObj.addProperty("name", rs.getString(2));
		jArr.add(jObj);
	}
}
	con.close();
	System.out.println(jArr.toString());
   return jArr.toString();
}	



}
