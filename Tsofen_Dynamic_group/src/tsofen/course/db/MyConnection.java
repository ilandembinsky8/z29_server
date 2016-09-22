package tsofen.course.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyConnection {
	
	//private DbHandler handler;
	private Connection con;
	private static MyConnection _instance = null;
	private final String url = "jdbc:mysql://tsofen29db.csks0itfccec.eu-central-1.rds.amazonaws.com:3306/Updated";
	private final String username = "Tsofen29DBUser";
	private final String pass = "Tsofen29SQL";
	
	public MyConnection() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
	
		con=DriverManager.getConnection(url,username,pass);		
		
	}
	
	private void connectToDB() throws SQLException{
		con = DriverManager.getConnection(url ,username ,pass);
		//st =  con.createStatement();
	}

	public static MyConnection getinstance() throws ClassNotFoundException, SQLException {
		if(_instance == null)
			_instance = new MyConnection();
		return _instance;
	}
	
	public Connection getCon(){
		return con;
		
	}
	
	public void closeCon() throws SQLException
	{
		con.close();
	}


}
