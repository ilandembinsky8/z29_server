package tsofen.course.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyConnection {
	
	private DbHandler handler;
	
	//Connection con;
	
	
	public MyConnection() throws ClassNotFoundException, SQLException{
		
		//Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/test1";
		String user="root"; 
		String pass="12345"; 
	//	con=DriverManager.getConnection(url, "root1", "");
		handler=new DbHandler(url,user,pass);
		
		
	}
/*

	public Connection getCon() {
		return con;
	}
	
	
	public void closeCon() throws SQLException
	{
		
		con.close();
	}
*/

	public DbHandler getHandler() {
		return handler;
	}


}
