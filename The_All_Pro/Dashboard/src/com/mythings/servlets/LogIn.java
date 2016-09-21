package com.mythings.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import com.mythings.classes.DBManager;

/**
 * Servlet implementation class LogIn
 */
@WebServlet("/login")
public class LogIn extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private JsonObject jo;  
    private PrintWriter out;
    private DBManager dbm;
    
    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @see HttpServlet#HttpServlet()
     */
    public LogIn() throws ClassNotFoundException, SQLException { 
    	super();
		dbm = DBManager.getInstance();
    }

	
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		jo = new JsonObject();
		out = response.getWriter();
		
		//if((null == request.getSession().getAttribute("isLoggedIn")) ||
		   //(null != request.getSession().getAttribute("isLoggedIn") && request.getSession().getAttribute("isLoggedIn").equals(false))){
		if(null == request.getSession().getAttribute("isLoggedIn")){
			jo.addProperty("isLogged", false);
			jo.addProperty("redirectPath", "login.html");
			out.print(jo);
			
		}else{
			jo.addProperty("isLogged", true);
			out.print(jo);
		}
		
		out.close();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			jo = new JsonObject();
			out = response.getWriter();
			
			if(!dbm.checkID(request.getParameter("id"))){
				
				jo.addProperty("message", "This ID is Not Exists !");
				jo.addProperty("success", false);
				out.print(jo);
			}
			else if(!dbm.checkPass(request.getParameter("id"), request.getParameter("pass"))){
				
				jo.addProperty("message", "The Password is incorect !");
				jo.addProperty("success", false);
				out.print(jo);
			}
			else{
				
				request.getSession().setAttribute("isLoggedIn", true);
				jo.addProperty("redirectPath", "dashboard.html");
				jo.addProperty("success", true);
				out.print(jo);	
			}
			
			out.close();
			
		} catch (NumberFormatException e){
			
			jo.addProperty("message", "ID should be numeric");
			jo.addProperty("success", false);
			out.print(jo);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} 
	}
}
