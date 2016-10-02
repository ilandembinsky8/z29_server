package com.mythings.db;

import java.sql.SQLException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public SessionListener() {}

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {}

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent e)  { 
    	
    	MyConnection con = null;
		
    	try {
    		 HttpSession session = e.getSession();
    		 con = (MyConnection)session.getAttribute("connection");
    		 if (con != null) con.closeCon();
    		 if(session.getAttribute("user") != null)
    			 session.removeAttribute("user");
		} catch (SQLException e1) { e1.printStackTrace(); }
    }
	
}
