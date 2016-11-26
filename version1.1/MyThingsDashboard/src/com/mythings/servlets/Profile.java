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
import com.mythings.db.LoginQuery;
import com.mythings.db.MyConnection;
import com.mythings.db.ProfileQuery;

/**
 * Servlet implementation class Settings
 */
@WebServlet("/profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String id;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MyConnection con=null;
		PrintWriter out = response.getWriter();
		String func = request.getParameter("func");
		String pass = request.getParameter("pass");
		/*response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");*/
		
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
		    	
			
			try{
				if(func.equals("getAdminData")){
					id = request.getParameter("id");
					if(LoginQuery.checkPass(con.getCon(), id, pass))
						out.print(ProfileQuery.getAdminData(con.getCon(), id));
					else
						response.sendRedirect("login.html");
				}else if(func.equals("updateAdminData")){
					String email = request.getParameter("email");	
					out.print(ProfileQuery.updateAdminData(con.getCon(), id, email, pass));
				}
			} catch (SQLException e) { e.printStackTrace(); }
			
			out.close();
	}
}
