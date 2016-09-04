package tsofen.course.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
	
	
	Connection con;
	
	
	public MyConnection() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/test";
		con=DriverManager.getConnection(url, "root1", "");
	}


	public Connection getCon() {
		return con;
	}
	
	
	public void closeCon() throws SQLException
	{
		
		con.close();
	}

}
