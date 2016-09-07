package tsofen.course.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyConnection {
	
	//private DbHandler handler;
	private Connection con;
	
	
	public MyConnection() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://tsofen29db.csks0itfccec.eu-central-1.rds.amazonaws.com:3306/Tsofen29Database";
		String user="Tsofen29DBUser"; 
		String pass="Tsofen29SQL"; 
		con=DriverManager.getConnection(url,user,pass);		
		
	}


	public Connection getCon() {
		return con;
	}
	
	
	public void closeCon() throws SQLException
	{
		
		con.close();
	}


}
