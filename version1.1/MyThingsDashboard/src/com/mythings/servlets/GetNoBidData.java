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
import com.mythings.db.MyConnection;
import com.mythings.db.NoBidQuery;

/**
 * Servlet implementation class HandlerServlet
 */
@WebServlet("/getdata")
public class GetNoBidData extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private static String exchangeId,refDate,compDate,refHour,compHour,reasons;
    private int advId;
	/**
     * Default constructor. 
     */
    public GetNoBidData() { super(); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MyConnection con=null;
		PrintWriter out = response.getWriter();
		String func = request.getParameter("func");
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
	    try {
	    	HttpSession session = request.getSession();
	    	con = (MyConnection)session.getAttribute("connection");
	    	if (con == null) {
	    		con = new MyConnection();
	    		session.setAttribute("connection",con);
	    	}	
		} 
	    catch (ClassNotFoundException e) { e.printStackTrace(); } 
	    catch (SQLException e) { e.printStackTrace(); }
	    	
		
		try {
			
			if(func.equals("getEx")){
				
				out.print(NoBidQuery.getExchange(con.getCon()));
			}
			else if(func.equals("getReasons")){
				
				exchangeId = request.getParameter("exchId");
				refHour = request.getParameter("refHour");
				compHour = request.getParameter("compHour");
				refDate = convertDate(request.getParameter("refDate"));
				compDate = convertDate(request.getParameter("compDate"));
				out.print(NoBidQuery.getReasons(con.getCon(), exchangeId,refDate,refHour,compDate,compHour));
			}
			else if(func.equals("getAdv")){
			
				reasons = request.getParameter("reasons");
				out.print(NoBidQuery.getAdv(con.getCon(),refDate,refHour,compHour,compDate,reasons));		
			}
			else if(func.equals("getAdvTree")){
				
				advId = Integer.parseInt(request.getParameter("advId"));
				String advName = request.getParameter("advName");
				out.print(NoBidQuery.getAdvTree(con.getCon(),refDate,refHour,compHour,compDate,reasons,advId,advName));
			}
			else if(func.equals("getTree")){
		
				reasons = request.getParameter("reasons");
				out.print(NoBidQuery.prepareTree(con.getCon(),refDate,refHour,compHour,compDate,reasons));
			}
			else if(func.equals("getCamps")){
				
				advId = Integer.parseInt(request.getParameter("advId"));
				out.print(NoBidQuery.getCampaign(con.getCon(),refDate,refHour,compHour,compDate, advId));
			}

		} catch (SQLException e) { e.printStackTrace(); }
		
		out.close();
	}
	
	private String convertDate(String s){
		String day = s.substring(3, 5);
		String month = s.substring(0, 2);
		String year = s.substring(6);
		return year + "-" + month + "-" + day;
	}
}