package com.mythings.servlets;

import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;
import com.mythings.db.MyConnection;
import com.mythings.db.NoBidQuery;

/**
 * Servlet implementation class HandlerServlet
 */
@WebServlet("/getdata")
public class GetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * Default constructor. 
     */
    public GetData() { super(); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MyConnection con=null;
		PrintWriter out = response.getWriter();
		String exchangeId,noBidId, advCampId, campaignId;
		List noBidIds =new ArrayList<String>();
		String func = request.getParameter("func");
		
	    try {
	    	HttpSession session = request.getSession();
	    	con = (MyConnection)session.getAttribute("connection");
	    	if (con == null) {
	    		
	    		con = new MyConnection();
	    		session.setAttribute("connection",con);
	    	}	
		} 
	    catch (ClassNotFoundException e1) { e1.printStackTrace(); } 
	    catch (SQLException e1) { e1.printStackTrace(); }
	    	
		
		try {
			
			if(func.equals("getEx")){
				
				out.print(NoBidQuery.getExchange(con.getCon()));
			}
			else if(func.equals("getNoBid")){
				
				exchangeId = request.getParameter("exchId");
				out.print(NoBidQuery.getNoBidReasons(con.getCon(), exchangeId));
				System.out.println("getNoBidReasons Finished");
			}
			else if(func.equals("getAdv")){
				
				exchangeId = request.getParameter("exchId");
				noBidId = request.getParameter("noBidId");
				Gson gson = new Gson();
				JsonParser jsonParser = new JsonParser();
				JsonArray jsonArray = (JsonArray) jsonParser.parse(noBidId);
				
				if (jsonArray != null) { 
				   int len = jsonArray.size();
				   for (int i=0;i<len;i++){ 
					   noBidIds.add(jsonArray.get(i).toString());
				   } 
				}
	            
				out.print(NoBidQuery.getAdv(con.getCon(),exchangeId,noBidIds));
			}
			else if(func.equals("getAdvCompaign")){
				
				exchangeId = request.getParameter("exchId");
				noBidId = request.getParameter("noBidId");
				advCampId = request.getParameter("Advcampid");

			//	out.print(QueryResults.getAdvCompaign(con.getCon(), exchangeId, noBidId,advCampId));

			}
			else if(func.equals("getAdGroup")){
				
				exchangeId = request.getParameter("exchId");
				noBidId = request.getParameter("noBidId");
				advCampId = request.getParameter("advCampId");
			    campaignId = request.getParameter("compaignId");
				//out.print(QueryResults.getAdGroup(con.getCon(),exchangeId,noBidId,advCampId,idCompaign));
			}

//			else if(func.equals("getGraph")){
//				String Exchangeid= request.getParameter("exchID");
//				String nobid=request.getParameter("noBidID");
//				out.print(NoBidMethods.getExample(con.getCon(), Exchangeid, nobid));
//			}
			

		} catch (SQLException e) { e.printStackTrace(); }
		
		out.close();
	}
}
