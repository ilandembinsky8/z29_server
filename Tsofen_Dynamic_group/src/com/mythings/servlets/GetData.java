package com.mythings.servlets;

import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mythings.db.MyConnection;
import com.mythings.db.NoBidMethods;

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
		
	    	try {
	    		 HttpSession session = request.getSession(  );
	    		   con = 
	    		     (MyConnection)session.getAttribute("connection");
	    		    if (con == null) {
	    		    	con = new MyConnection();
	    		    	session.setAttribute("connection",con);
	    		    }
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	   
	

		PrintWriter out;
		
		try {
			 
			out = response.getWriter();
			String func = request.getParameter("func");
			
			if(func.equals("getEx"))
				out.print(NoBidMethods.getExchange(con.getCon()));
			
			if(func.equals("getNoBid")){
				String Exchangeid= request.getParameter("exchID");
			
				out.print(NoBidMethods.getNoBidReason(con.getCon(), Exchangeid));
			}
			else if(func.equals("getAd")){
				//String exchangeid= request.getParameter("exchID");
				//String nobid=request.getParameter("noBidID");
			}
			else if(func.equals("getAdvCompaign")){
				String Exchangeid= request.getParameter("exchID");
				String nobid=request.getParameter("noBidID");
				String Advcampid=request.getParameter("Advcampid");

				out.print(NoBidMethods.getAdvCompaign(con.getCon(), Exchangeid, nobid,Advcampid));

			}
			else if(func.equals("getAdGroup")){
				String Exchangeid= request.getParameter("exchID");
				String nobid=request.getParameter("noBidID");
				String Advcampid=request.getParameter("Advcampid");
				String idCompaign=request.getParameter("idCompaign");

				out.print(NoBidMethods.getAdGroup(con.getCon(),Exchangeid,nobid,Advcampid,idCompaign));

			}

//			else if(func.equals("getGraph")){
//				String Exchangeid= request.getParameter("exchID");
//				String nobid=request.getParameter("noBidID");
//				out.print(NoBidMethods.getExample(con.getCon(), Exchangeid, nobid));
//			}
			
			//con.closeCon();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
