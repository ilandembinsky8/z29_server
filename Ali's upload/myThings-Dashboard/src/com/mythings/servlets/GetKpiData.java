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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MyConnection con=null;
		PrintWriter out;
		
	    try {
	    	HttpSession session = request.getSession();
	    	con = (MyConnection)session.getAttribute("connection");
	    	if (con == null) {
	    		
	    		con = new MyConnection();
	    		session.setAttribute("connection",con);
	    	}	
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    	
		
		try {
			 
			out = response.getWriter();
			String func = request.getParameter("func");
			
			if(func.equals("getEx")){
				
				out.print(KpiQuery.getExchange(con.getCon()));
			}
			else if(func.equals("getAdv")){
				
				String exchangeid = request.getParameter("exchID");
				out.print(KpiQuery.getAdv(con.getCon(),exchangeid));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
