
// from Avgana

package com.mythings.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class NoBidQuery {

	private static Statement st;
	private static String query;
	private static int[] refTotalCount = new int[4];
	private static int[] compTotalCount = new int[4];
	private NoBidQuery() {}

	public static String getExchange(Connection con) throws SQLException {

		st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM Updated.NOBID_PRT_CAMPAIGN;");
		JsonArray jArr = new JsonArray();
		JsonObject jObj;
		while (rs.next()) {
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}

		return jArr.toString();
	}

	public static String getNoBidReasons(Connection con, String exchangeId)throws SQLException {

		JsonArray jArr = new JsonArray();
		int id = Integer.parseInt(exchangeId);

		query = "SELECT distinct REASON_TYPE.NO_BID_REASON_TYPE_ID,REASON_TYPE.NO_BID_REASON_TYPE"
				+ " FROM REASON_TYPE inner join nobid_reasons_main_updated"
				+ " ON nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID=REASON_TYPE.NO_BID_REASON_TYPE_ID"
				+ " WHERE nobid_reasons_main_updated.PRT_CAMPAIGN_ID=" + id;

		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		JsonObject jObj;
		while (rs.next()) {
			jObj = new JsonObject();
			jObj.addProperty("id", rs.getInt(1));
			jObj.addProperty("name", rs.getString(2));
			jArr.add(jObj);
		}

		return jArr.toString();
	}

	public static String getAdv(Connection con, String exchangeId, String date/*, String hour*/, String compToDate, String noBidId) throws SQLException {
//***************convert noDibReasonArray to String Array ****************		
		String[] strArr = noBidId.toString().split(",");
		String queryNobid = ""+strArr[0];
//***********************************************************************		
		System.out.println("start getAdv.....");
		System.out.println(date);
		JsonArray jArr = new JsonArray();
		int id = Integer.parseInt(exchangeId);
		JsonObject root = new JsonObject();
		for(int i=1;i<strArr.length;i++)
			queryNobid+=" OR nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="+strArr[i];
		query = /*"SELECT DISTINCT ADV_PROJECT.ADV_PROJECT_ID,ADV_PROJECT.ADV_PROJECT_NAME"
				+ " FROM ADV_PROJECT,nobid_reasons_main_updated WHERE nobid_reasons_main_updated.PRT_CAMPAIGN_ID="
				+ id
				+ " AND"
				+ " nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="
				+ noBidId[i]
				+ " AND"
				+ " ADV_PROJECT.ADV_PROJECT_ID=nobid_reasons_main_updated.ADV_PROJECT_ID"
				+ " LIMIT 3";*/
				"select distinct ADV_PROJECT.ADV_PROJECT_ID,ADV_PROJECT.ADV_PROJECT_NAME,nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID,sum(nobid_reasons_main_updated.cnt) as counter"
				+ " from ADV_PROJECT,nobid_reasons_main_updated where nobid_reasons_main_updated.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID"
				+ " and nobid_reasons_main_updated.PRT_CAMPAIGN_ID="
				+ id
				+ " AND"
				+ " (nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="
				+ queryNobid
				/*+ " AND"
				+ " nobid_reasons_main_updated.EVENT_TIME="
				+ hour*/
				+ ") and nobid_reasons_main_updated.EVENT_DATE=\'"
				+ date + "\'" 
				+ " group by ADV_PROJECT_ID"
				+ " LIMIT 3";

		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		JsonObject jObj = new JsonObject();
		refTotalCount[0] = 0;
		compTotalCount[0] = 0;
		int i=0;
		while (rs.next()) {
			
			jObj = new JsonObject();
			jObj.addProperty("name", rs.getString(2));
			jObj.add("children", getAdvCompaign(con, exchangeId, rs.getString(3),rs.getInt(1), date,compToDate).getAsJsonArray());
			refTotalCount[0] += refTotalCount[1];
			compTotalCount[0] += compTotalCount[1];
			jObj.addProperty("count",refTotalCount[1]);
			jObj.addProperty("cntComp",compTotalCount[1]);
			jArr.add(jObj);
		}

		System.out.println("Finished getAdv");
		root.addProperty("name", "Global");
		root.add("children", jArr.getAsJsonArray());
		root.addProperty("count",refTotalCount[0]);
		root.addProperty("cntComp",compTotalCount[0]);
		System.out.println(root.toString());
		return root.toString().replace(" ", "");
	}

	public static JsonArray getAdvCompaign(Connection con,String exchangeId, String noBidId, int idAdv, String date,String compToDate) throws SQLException {

		JsonArray jArr = new JsonArray();
		int id = Integer.parseInt(exchangeId);

		System.out.println("add compain");

		query = "select distinct ADV_CAMPAIGN.ADV_CAMPAIGN_ID,ADV_CAMPAIGN.ADV_CAMPAIGN_NAME"
				+ " from ADV_PROJECT,nobid_reasons_main_updated,ADV_CAMPAIGN"
				+ " where nobid_reasons_main_updated.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID and"
				+ " nobid_reasons_main_updated.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID"
				+ " and nobid_reasons_main_updated.PRT_CAMPAIGN_ID="
				+ id
				+ " AND nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="
				+ noBidId
				+ " and nobid_reasons_main_updated.EVENT_DATE=\'"
				+ date + "\'" 
				+ " and ADV_PROJECT.ADV_PROJECT_ID=" + idAdv + " LIMIT 4";

		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		JsonObject jObj;
		refTotalCount[1] = 0;
		compTotalCount[1] = 0;
		while (rs.next()) {
			jObj = new JsonObject();
			jObj.addProperty("name", rs.getString(2));
			jObj.add("children", getAdGroup(con, exchangeId, noBidId, idAdv, rs.getInt(1), date,compToDate).getAsJsonArray());
			refTotalCount[1] += refTotalCount[2];
			compTotalCount[1] += compTotalCount[2];
			jObj.addProperty("count",refTotalCount[2]);
			jObj.addProperty("cntComp",compTotalCount[2]);
			jArr.add(jObj);
		}
		System.out.println("Finished getAdvCampaign");
		return jArr;
	}

	public static JsonArray getAdGroup(Connection con, String exchangeId, String noBidId, int idAdv, int idCompaign, String date,String compToDate) throws SQLException {

		System.out.println("add adgroup for " + idCompaign);

		JsonArray jArr = new JsonArray();
		int id = Integer.parseInt(exchangeId);

		query = "select distinct ADGROUP.ADGROUP_ID ,ADGROUP.AD_GROUP_NAME"
				+ " from ADV_PROJECT,nobid_reasons_main_updated,ADV_CAMPAIGN,ADGROUP"
				+ " where nobid_reasons_main_updated.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID"
				+ " and nobid_reasons_main_updated.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID"
				+ " and nobid_reasons_main_updated.ADGROUP_ID=ADGROUP.ADGROUP_ID"
				+ " and nobid_reasons_main_updated.PRT_CAMPAIGN_ID=" + id
				+ " and nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID=" + noBidId
				+ " and ADV_PROJECT.ADV_PROJECT_ID=" + idAdv
				+ " and ADV_CAMPAIGN.ADV_CAMPAIGN_ID=" + idCompaign
				+ " and nobid_reasons_main_updated.EVENT_DATE=\'"
				+ date + "\'" 
				+ " LIMIT 5";

		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		JsonObject jObj;
		refTotalCount[2] = 0;
		compTotalCount[2] = 0;
		while (rs.next()) {
			jObj = new JsonObject();
			jObj.addProperty("name", rs.getString(2));
			//lina
			jObj.add("children", getCreatives(con, exchangeId, noBidId, idAdv, idCompaign, rs.getInt(1),date, compToDate).getAsJsonArray());
			refTotalCount[2] += refTotalCount[3];
			compTotalCount[2] += compTotalCount[3];
			jObj.addProperty("count",refTotalCount[3]);
			jObj.addProperty("cntComp",compTotalCount[3]);
			jArr.add(jObj);
		}
		System.out.println("Finished adGroup");
		return jArr;
	}

	//lina
	public static JsonArray getCreatives(Connection con, String exchangeId, String noBidId, int advId, int idCampaign, int adGroup, String date,String compToDate) throws SQLException {

		System.out.println("getCreatives Started");
		System.out.println("add Creative for " + adGroup);
		
		query = "SELECT distinct ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID, ADV_PROJECT_CREATIVE.CREATIVE_NAME"
				+ " FROM ADV_PROJECT,nobid_reasons_main_updated,ADV_CAMPAIGN,ADGROUP,ADV_PROJECT_CREATIVE"
				+ " WHERE nobid_reasons_main_updated.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID "
				+ " AND nobid_reasons_main_updated.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID and"
				+ " nobid_reasons_main_updated.ADGROUP_ID=ADGROUP.ADGROUP_ID and nobid_reasons_main_updated.ADV_PROJECT_CREATIVE_ID=ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID"
				+ " AND nobid_reasons_main_updated.PRT_CAMPAIGN_ID="
				+ exchangeId
				+ " AND nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="
				+ noBidId
				+ " AND ADV_PROJECT.ADV_PROJECT_ID="
				+ advId
				+ " AND ADV_CAMPAIGN.ADV_CAMPAIGN_ID="
				+ idCampaign
				+ " AND ADGROUP.ADGROUP_ID=" + adGroup
				+ " and nobid_reasons_main_updated.EVENT_DATE=\'"
				+ date + "\'";

//		st = con.createStatement();
//		ResultSet rs = st.executeQuery(query);
		
//		ResultSet rsComp = st.executeQuery(query);
		
		refTotalCount[3]= 0;
		compTotalCount[3]= 0;
		
		JsonArray jArr = calculateCount(con,exchangeId,noBidId, advId, idCampaign,  adGroup,date,query,"count", 1);
//		jArr = calculateCount(con,exchangeId,noBidId, advId, idCampaign,  adGroup,date, compToDate,query,"cntComp");
		date = compToDate;
		query = "SELECT distinct ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID, ADV_PROJECT_CREATIVE.CREATIVE_NAME"
				+ " FROM ADV_PROJECT,nobid_reasons_main_updated,ADV_CAMPAIGN,ADGROUP,ADV_PROJECT_CREATIVE"
				+ " WHERE nobid_reasons_main_updated.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID "
				+ " AND nobid_reasons_main_updated.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID and"
				+ " nobid_reasons_main_updated.ADGROUP_ID=ADGROUP.ADGROUP_ID and nobid_reasons_main_updated.ADV_PROJECT_CREATIVE_ID=ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID"
				+ " AND nobid_reasons_main_updated.PRT_CAMPAIGN_ID="
				+ exchangeId
				+ " AND nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="
				+ noBidId
				+ " AND ADV_PROJECT.ADV_PROJECT_ID="
				+ advId
				+ " AND ADV_CAMPAIGN.ADV_CAMPAIGN_ID="
				+ idCampaign
				+ " AND ADGROUP.ADGROUP_ID=" + adGroup
				+ " and nobid_reasons_main_updated.EVENT_DATE=\'"
				+ date + "\'";
		
		JsonArray jArr1 = (calculateCount(con,exchangeId,noBidId, advId, idCampaign,  adGroup, compToDate,query,"cntComp", 2));
//		jArr1.
//		JsonObject a = new JsonObject();
//		jArr1.get(0).getAsJsonObject().get("cntComp");
		for(int i=0;i<jArr.size();i++){
			JsonObject a = jArr1.get(i).getAsJsonObject();
			String c = a.get("cntComp").toString();
//			int b = jArr1.get(0).getAsInt();
			c = c.replaceAll("\"", "");
			jArr.get(i).getAsJsonObject().addProperty("cntComp",c );
		}
		
		

		System.out.println("getCreatives Finished");
		System.out.println("1.jArr = " + jArr.toString());
		System.out.println("2.jArr = " + jArr1.toString());
		return jArr;
	}
	
	public static JsonArray calculateCount(Connection con, String exchangeId, String noBidId, int advId, int idCampaign, int adGroup, String date, String query, String name, int current) throws NumberFormatException, SQLException{
		JsonArray jArr = new JsonArray();
		JsonObject jObj ;
		//jObj.a
		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while (rs.next()) {
			String refCnt;
			
			int num;
			jObj = new JsonObject();
			jObj.addProperty("name", rs.getString(2));	
			refCnt = getCount(con, exchangeId, noBidId, advId, idCampaign, adGroup, rs.getInt(1), date);
			
			//lina
			
			//String compCnt = getCount(con, exchangeId, noBidId, advId, idCampaign, adGroup, rs.getInt(1), compToDate);
		
			num = Integer.parseInt(refCnt);
			System.out.println("num="+num);
			if(current == 1)
				refTotalCount[3] += num;
			else if(current == 2)
				compTotalCount[3] += num;
			jObj.addProperty(name, refCnt);	
			
			//System.out.print("compareToDate="+compToDate);
			
		//	num = Integer.parseInt(compCnt);
			//jObj.addProperty("cntComp", compCnt);
		//	compTotalCount[3] += num;
			
			 jArr.add(jObj);
		}
		
		return jArr;
	}
	public static String getCount(Connection con, String exchangeId, String noBidId, int advId, int idCampaign, int adGroup, int creative, String date) throws SQLException{
		query = "SELECT nobid_reasons_main_updated.EVENT_TIME, SUM(nobid_reasons_main_updated.CNT) AS COUNTER"
				+" FROM ADV_PROJECT,nobid_reasons_main_updated,ADV_CAMPAIGN,ADGROUP,ADV_PROJECT_CREATIVE"
				+" WHERE  nobid_reasons_main_updated.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID and"
				 +" nobid_reasons_main_updated.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID" 
				 +" and nobid_reasons_main_updated.ADGROUP_ID=ADGROUP.ADGROUP_ID" 
				 +" and nobid_reasons_main_updated.ADV_PROJECT_CREATIVE_ID=ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID"
				+" and nobid_reasons_main_updated.PRT_CAMPAIGN_ID="+exchangeId
				+" and nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="+noBidId 
				+" and ADV_PROJECT.ADV_PROJECT_ID="+advId
				+" and ADV_CAMPAIGN.ADV_CAMPAIGN_ID="+idCampaign
				+" and ADGROUP.ADGROUP_ID="+adGroup
				+" AND nobid_reasons_main_updated.ADV_PROJECT_CREATIVE_ID="+creative
				+" and nobid_reasons_main_updated.EVENT_TIME=3"
				+ " and nobid_reasons_main_updated.EVENT_DATE=\'"
				+ date + "\'" ;
		st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		int count = 0;// = rs.getInt(2);
//		rs.next();
//		count = rs.getInt(2);
		while(rs.next()){
			count += rs.getInt(2);
		}
//		JsonObject val = new JsonObject();
//		val.addProperty("name", count);
		return ""+count;
	}
}



//End Avgana





//package com.mythings.db;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//
//public class NoBidQuery {
//
//	private static Statement st;
//	private static String query;
//	private static int[] counts = new int[4];
//	private NoBidQuery() {}
//
//	public static String getExchange(Connection con) throws SQLException {
//
//		st = con.createStatement();
//		ResultSet rs = st.executeQuery("SELECT * FROM Updated.NOBID_PRT_CAMPAIGN;");
//		JsonArray jArr = new JsonArray();
//		JsonObject jObj;
//		while (rs.next()) {
//			jObj = new JsonObject();
//			jObj.addProperty("id", rs.getInt(1));
//			jObj.addProperty("name", rs.getString(2));
//			jArr.add(jObj);
//		}
//
//		return jArr.toString();
//	}
//
//	public static String getNoBidReasons(Connection con, String exchangeId)throws SQLException {
//
//		JsonArray jArr = new JsonArray();
//		int id = Integer.parseInt(exchangeId);
//
//		query = "SELECT distinct REASON_TYPE.NO_BID_REASON_TYPE_ID,REASON_TYPE.NO_BID_REASON_TYPE"
//				+ " FROM REASON_TYPE inner join nobid_reasons_main_updated"
//				+ " ON nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID=REASON_TYPE.NO_BID_REASON_TYPE_ID"
//				+ " WHERE nobid_reasons_main_updated.PRT_CAMPAIGN_ID=" + id;
//
//		st = con.createStatement();
//		ResultSet rs = st.executeQuery(query);
//		JsonObject jObj;
//		while (rs.next()) {
//			jObj = new JsonObject();
//			jObj.addProperty("id", rs.getInt(1));
//			jObj.addProperty("name", rs.getString(2));
//			jArr.add(jObj);
//		}
//
//		return jArr.toString();
//	}
//
//	public static String getAdv(Connection con, String exchangeId, String date/*, String hour*/, String... noBidId) throws SQLException {
//
//		System.out.println("start getAdv.....");
//		System.out.println(date);
////		String d = "\'" + date + "\'";
////		date = d;
//		System.out.println(date);
//		JsonArray jArr = new JsonArray();
//		int id = Integer.parseInt(exchangeId);
//		JsonObject root = new JsonObject();
//		for (int i = 0; i < noBidId.length; i++) {
//			query = /*"SELECT DISTINCT ADV_PROJECT.ADV_PROJECT_ID,ADV_PROJECT.ADV_PROJECT_NAME"
//					+ " FROM ADV_PROJECT,nobid_reasons_main_updated WHERE nobid_reasons_main_updated.PRT_CAMPAIGN_ID="
//					+ id
//					+ " AND"
//					+ " nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="
//					+ noBidId[i]
//					+ " AND"
//					+ " ADV_PROJECT.ADV_PROJECT_ID=nobid_reasons_main_updated.ADV_PROJECT_ID"
//					+ " LIMIT 3";*/
//					"select distinct ADV_PROJECT.ADV_PROJECT_ID,ADV_PROJECT.ADV_PROJECT_NAME,sum(nobid_reasons_main_updated.cnt) as counter"
//					+ " from ADV_PROJECT,nobid_reasons_main_updated where nobid_reasons_main_updated.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID"
//					+ " and nobid_reasons_main_updated.PRT_CAMPAIGN_ID="
//					+ id
//					+ " AND"
//					+ " nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="
//					+ noBidId[i]
//					/*+ " AND"
//					+ " nobid_reasons_main_updated.EVENT_TIME="
//					+ hour*/
//					+ " and nobid_reasons_main_updated.EVENT_DATE=\'"
//					+ date + "\'" 
//					+ " group by ADV_PROJECT_ID"
//					+ " LIMIT 3";
//
//			st = con.createStatement();
//			ResultSet rs = st.executeQuery(query);
//			JsonObject jObj = new JsonObject();
//			counts[0] = 0;
//			while (rs.next()) {
//				jObj = new JsonObject();
//				jObj.addProperty("name", rs.getString(2));
//				jObj.add("children", getAdvCompaign(con, exchangeId, noBidId[0],rs.getInt(1), date).getAsJsonArray());
//				counts[0] += counts[1];
//				jObj.addProperty("count",counts[1]);
//				jArr.add(jObj);
//			}
//
//			System.out.println("Finished getAdv");
//		}
//		root.addProperty("name", "Global");
//		root.add("children", jArr.getAsJsonArray());
//		root.addProperty("count",counts[0]);
//		System.out.println(root.toString());
//		return root.toString().replace(" ", "");
//	}
//
//	public static JsonArray getAdvCompaign(Connection con,String exchangeId, String noBidId, int idAdv, String date) throws SQLException {
//
//		JsonArray jArr = new JsonArray();
//		int id = Integer.parseInt(exchangeId);
//
//		System.out.println("add compain");
//
//		query = "select distinct ADV_CAMPAIGN.ADV_CAMPAIGN_ID,ADV_CAMPAIGN.ADV_CAMPAIGN_NAME"
//				+ " from ADV_PROJECT,nobid_reasons_main_updated,ADV_CAMPAIGN"
//				+ " where nobid_reasons_main_updated.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID and"
//				+ " nobid_reasons_main_updated.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID"
//				+ " and nobid_reasons_main_updated.PRT_CAMPAIGN_ID="
//				+ id
//				+ " AND nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="
//				+ noBidId
//				+ " and nobid_reasons_main_updated.EVENT_DATE=\'"
//				+ date + "\'" 
//				+ " and ADV_PROJECT.ADV_PROJECT_ID=" + idAdv + " LIMIT 4";
//
//		st = con.createStatement();
//		ResultSet rs = st.executeQuery(query);
//		JsonObject jObj;
//		counts[1] = 0;
//		while (rs.next()) {
//			jObj = new JsonObject();
//			jObj.addProperty("name", rs.getString(2));
//			jObj.add("children", getAdGroup(con, exchangeId, noBidId, idAdv, rs.getInt(1), date).getAsJsonArray());
//			counts[1] += counts[2];
//			jObj.addProperty("count",counts[2]);
//			jArr.add(jObj);
//		}
//		System.out.println("Finished getAdvCampaign");
//		return jArr;
//	}
//
//	public static JsonArray getAdGroup(Connection con, String exchangeId, String noBidId, int idAdv, int idCompaign, String date) throws SQLException {
//
//		System.out.println("add adgroup for " + idCompaign);
//
//		JsonArray jArr = new JsonArray();
//		int id = Integer.parseInt(exchangeId);
//
//		query = "select distinct ADGROUP.ADGROUP_ID ,ADGROUP.AD_GROUP_NAME"
//				+ " from ADV_PROJECT,nobid_reasons_main_updated,ADV_CAMPAIGN,ADGROUP"
//				+ " where nobid_reasons_main_updated.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID"
//				+ " and nobid_reasons_main_updated.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID"
//				+ " and nobid_reasons_main_updated.ADGROUP_ID=ADGROUP.ADGROUP_ID"
//				+ " and nobid_reasons_main_updated.PRT_CAMPAIGN_ID=" + id
//				+ " and nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID=" + noBidId
//				+ " and ADV_PROJECT.ADV_PROJECT_ID=" + idAdv
//				+ " and ADV_CAMPAIGN.ADV_CAMPAIGN_ID=" + idCompaign
//				+ " and nobid_reasons_main_updated.EVENT_DATE=\'"
//				+ date + "\'" 
//				+ " LIMIT 5";
//
//		st = con.createStatement();
//		ResultSet rs = st.executeQuery(query);
//		JsonObject jObj;
//		counts[2] = 0;
//		while (rs.next()) {
//			jObj = new JsonObject();
//			jObj.addProperty("name", rs.getString(2));
//			jObj.add("children", getCreatives(con, exchangeId, noBidId, idAdv, idCompaign, rs.getInt(1),date).getAsJsonArray());
//			counts[2] += counts[3];
//			jObj.addProperty("count",counts[3]);
//			jArr.add(jObj);
//		}
//		System.out.println("Finished adGroup");
//		return jArr;
//	}
//
//	public static JsonArray getCreatives(Connection con, String exchangeId, String noBidId, int advId, int idCampaign, int adGroup, String date) throws SQLException {
//
//		System.out.println("getCreatives Started");
//		System.out.println("add Creative for " + adGroup);
//
//		query = "SELECT distinct ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID, ADV_PROJECT_CREATIVE.CREATIVE_NAME"
//				+ " FROM ADV_PROJECT,nobid_reasons_main_updated,ADV_CAMPAIGN,ADGROUP,ADV_PROJECT_CREATIVE"
//				+ " WHERE nobid_reasons_main_updated.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID "
//				+ " AND nobid_reasons_main_updated.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID and"
//				+ " nobid_reasons_main_updated.ADGROUP_ID=ADGROUP.ADGROUP_ID and nobid_reasons_main_updated.ADV_PROJECT_CREATIVE_ID=ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID"
//				+ " AND nobid_reasons_main_updated.PRT_CAMPAIGN_ID="
//				+ exchangeId
//				+ " AND nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="
//				+ noBidId
//				+ " AND ADV_PROJECT.ADV_PROJECT_ID="
//				+ advId
//				+ " AND ADV_CAMPAIGN.ADV_CAMPAIGN_ID="
//				+ idCampaign
//				+ " AND ADGROUP.ADGROUP_ID=" + adGroup
//				+ " and nobid_reasons_main_updated.EVENT_DATE=\'"
//				+ date + "\'";
//
//		st = con.createStatement();
//		ResultSet rs = st.executeQuery(query);
//		JsonArray jArr = new JsonArray();
//		JsonObject jObj;
//		counts[3]= 0;
//		while (rs.next()) {
//			String str;
//			int num;
//			jObj = new JsonObject();
//			jObj.addProperty("name", rs.getString(2));
//			str = getCount(con, exchangeId, noBidId, advId, idCampaign, adGroup, rs.getInt(1), date);
//			num = Integer.parseInt(str);
//			jObj.addProperty("count", str);
//			counts[3] += num;
//			jArr.add(jObj);
//		}
//
//		System.out.println("getCreatives Finished");
//		return jArr;
//	}
//	public static String getCount(Connection con, String exchangeId, String noBidId, int advId, int idCampaign, int adGroup, int creative, String date) throws SQLException{
//		query = "SELECT nobid_reasons_main_updated.EVENT_TIME, SUM(nobid_reasons_main_updated.CNT) AS COUNTER"
//				+" FROM ADV_PROJECT,nobid_reasons_main_updated,ADV_CAMPAIGN,ADGROUP,ADV_PROJECT_CREATIVE"
//				+" WHERE  nobid_reasons_main_updated.ADV_PROJECT_ID=ADV_PROJECT.ADV_PROJECT_ID and"
//				 +" nobid_reasons_main_updated.ADV_CAMPAIGN_ID=ADV_CAMPAIGN.ADV_CAMPAIGN_ID" 
//				 +" and nobid_reasons_main_updated.ADGROUP_ID=ADGROUP.ADGROUP_ID" 
//				 +" and nobid_reasons_main_updated.ADV_PROJECT_CREATIVE_ID=ADV_PROJECT_CREATIVE.ADV_PROJECT_CREATIVE_ID"
//				+" and nobid_reasons_main_updated.PRT_CAMPAIGN_ID="+exchangeId
//				+" and nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="+noBidId 
//				+" and ADV_PROJECT.ADV_PROJECT_ID="+advId
//				+" and ADV_CAMPAIGN.ADV_CAMPAIGN_ID="+idCampaign
//				+" and ADGROUP.ADGROUP_ID="+adGroup
//				+" AND nobid_reasons_main_updated.ADV_PROJECT_CREATIVE_ID="+creative
//				+" and nobid_reasons_main_updated.EVENT_TIME=3"
//				+ " and nobid_reasons_main_updated.EVENT_DATE=\'"
//				+ date + "\'" ;
//		st = con.createStatement();
//		ResultSet rs = st.executeQuery(query);
//		int count = 0;// = rs.getInt(2);
////		rs.next();
////		count = rs.getInt(2);
//		while(rs.next()){
//			count += rs.getInt(2);
//		}
////		JsonObject val = new JsonObject();
////		val.addProperty("name", count);
//		return ""+count;
//	}
//	public static String getExample(Connection con, String exchangeId, String... noBidId) throws SQLException {
//
//		JsonArray jArr = new JsonArray();
//		int id = Integer.parseInt(exchangeId);
//
//		for (int i = 0; i < noBidId.length; i++)
//			query = "SELECT ADV_PROJECT.ADV_PROJECT_NAME , sum(nobid_reasons_main_updated.CNT)"
//					+ " FROM ADV_PROJECT,nobid_reasons_main_updated"
//					+ " WHERE nobid_reasons_main_updated.PRT_CAMPAIGN_ID="
//					+ id
//					+ " AND nobid_reasons_main_updated.NO_BID_REASON_TYPE_ID="
//					+ noBidId[i]
//					+ " and ADV_PROJECT.ADV_PROJECT_ID = nobid_reasons_main_updated.ADV_PROJECT_ID"
//					+ " group by ADV_PROJECT.ADV_PROJECT_NAME";
//
//		st = con.createStatement();
//		ResultSet rs = st.executeQuery(query);
//		JsonObject jObj = new JsonObject();
//		while (rs.next()) {
//			// jObj = new JsonObject();
//			// jObj.addProperty("Advertiser", rs.getString(1));
//			jObj.addProperty("Clicks", rs.getInt(2));
//			jArr.add(jObj);
//		}
//
//		return jArr.toString();
//	}
//
//}
