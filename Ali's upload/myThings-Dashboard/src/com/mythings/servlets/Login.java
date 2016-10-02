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
import com.google.gson.JsonObject;
import com.mythings.db.LoginQuery;
import com.mythings.db.MyConnection;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() { super(); }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		MyConnection con = null;
		PrintWriter out = response.getWriter();
		HttpSession session = null;
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		JsonObject jO = new JsonObject();
		
		try {
			session = request.getSession();
	    	con = (MyConnection)session.getAttribute("connection");
	    	
	    	if (con == null) {
	    		
	    		con = new MyConnection();
	    		session.setAttribute("connection",con);
	    	}	
			
			if(!LoginQuery.checkID(con.getCon(), id)){
				
				jO.addProperty("message", "This ID is Not Exists !");
				jO.addProperty("success", false);
			}
			else if(!LoginQuery.checkPass(con.getCon(), id, pass)){
				
				jO.addProperty("message", "The Password is incorect !");
				jO.addProperty("success", false);
			}
			else{
				
				request.getSession().setAttribute("isLoggedIn", true);
				jO.addProperty("redirectPath", "kpi");
				jO.addProperty("success", true);
				session.setAttribute("user", id);
			}
			
		} catch (NumberFormatException e){
			
			jO.addProperty("message", "ID should be numeric");
			jO.addProperty("success", false);
		}
		catch (SQLException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		
		out.print(jO);
		out.close();
	}
}
