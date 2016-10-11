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
		String date,exchangeId,noBidId;
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
	    catch (ClassNotFoundException e1) { e1.printStackTrace(); } 
	    catch (SQLException e1) { e1.printStackTrace(); }
	    	
		
		try {
			
			if(func.equals("getEx")){
				
				out.print(NoBidQuery.getExchange(con.getCon2()));
			}
			else if(func.equals("getNoBid")){
				
				exchangeId = request.getParameter("exchId");
				out.print(NoBidQuery.getNoBidReasons(con.getCon2(), exchangeId));
				System.out.println("getNoBidReasons Finished");
			}
			else if(func.equals("getAdv")){
				
			/*	exchangeId = request.getParameter("exchId");
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
	            
			
				out.print(NoBidQuery.getAdv(con.getCon2(),exchangeId,noBidIds));*/
		
				
				
		//******************changed by Avgana************************////  
				exchangeId = request.getParameter("exchId");
				noBidId = request.getParameter("noBidId");
				date = request.getParameter("refDate");
//				String s = NoBidQuery.getAdv(con.getCon(),exchangeId,noBidId);
//				System.out.println(s);
				System.out.println(date);
//				String day = date.substring(3, 5);
//				String month = date.substring(0, 2);
//				String year = date.substring(6, 10);
//				date = year + "-" + month + "-" + day;
				out.print(NoBidQuery.getAdv(con.getCon2(),exchangeId, date, noBidId));		
			}			

		} catch (SQLException e) { e.printStackTrace(); }
		
		out.close();
	}
}