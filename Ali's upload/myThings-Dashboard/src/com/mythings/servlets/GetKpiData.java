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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetKpiData() { super(); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		
		MyConnection con = null;
		PrintWriter out = null;
		String func = request.getParameter("func");
		HttpSession session = request.getSession();
		con = (MyConnection)session.getAttribute("connection");
		
		
		try { out = response.getWriter(); }
		catch (IOException e) { e.printStackTrace(); }
		
		if (con == null) {
    		
    		try { con = new MyConnection(); }
    		catch (ClassNotFoundException e) { e.printStackTrace();} 
    		catch (SQLException e) { e.printStackTrace(); }
    		
    		session.setAttribute("connection",con);
    	}	
	   
		
		try {
			if(func.equals("getEx")) 
				out.print(KpiQuery.getExchange(con.getCon()));
			else if(func.equals("getAdv")){
				
				String exchangeid = request.getParameter("exchID");
				out.print(KpiQuery.getAdv(con.getCon(),exchangeid));
			}
		}catch(SQLException e){ e.printStackTrace(); }
	}
}
