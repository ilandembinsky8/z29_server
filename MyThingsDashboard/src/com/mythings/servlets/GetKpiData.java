package com.mythings.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mythings.db.KpiQuery;
import com.mythings.db.MyConnection;


/**
 * Servlet implementation class GetKpiData
 */
@WebServlet("/getkpidata")
public class GetKpiData extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private static String fromDate,exchangeId, advId,campId,groupId,toDate;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetKpiData() { super(); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		MyConnection con = null;
		PrintWriter out = response.getWriter();
		String func = request.getParameter("func");
		HttpSession session = request.getSession();
		con = (MyConnection)session.getAttribute("connection");
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if (con == null) {
    		
    		try { con = new MyConnection(); }
    		catch (ClassNotFoundException e) { e.printStackTrace();} 
    		catch (SQLException e) { e.printStackTrace(); }
    		
    		session.setAttribute("connection",con);
    	}	
	   
		
		try {
			if(func.equals("getDates")){
				out.print(KpiQuery.getDates(con.getCon()));
			}
		    else if(func.equals("getExchanges")){
				out.print(KpiQuery.getExchanges(con.getCon()));
			}
			else if(func.equals("getAdvertisers")){
				
				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");
				exchangeId = request.getParameter("exchId");
				out.print(KpiQuery.getAdvertisers(con.getCon(),exchangeId,fromDate,toDate));
			}
			else if(func.equals("getCampaigns")){
				
				advId = request.getParameter("advId");
				out.print(KpiQuery.getCampaigns(con.getCon(),exchangeId,advId,fromDate,toDate));
			}
			
			else if(func.equals("getGroups")){
				
				campId = request.getParameter("campId");
				out.print(KpiQuery.getGroups(con.getCon(),exchangeId,advId,campId,fromDate,toDate));
			}
			else if(func.equals("getCreatives")){
				
				groupId = request.getParameter("groupId");
				out.print(KpiQuery.getCreatives(con.getCon(), exchangeId, advId, campId, groupId,fromDate, toDate));
			}
		}catch(SQLException e){ e.printStackTrace(); }
		
		out.close();
	}
}
