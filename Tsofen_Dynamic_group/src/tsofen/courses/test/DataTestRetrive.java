package tsofen.courses.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataTestRetrive {
	
	private int index;
	private String data;
	
	
	
	public DataTestRetrive() {
		super();
	
	}
	
	
		
	public DataTestRetrive(int index, String data) {
		super();
		this.index = index;
		this.data = data;
	}




	public DataTestRetrive getDataFromDb(Connection con,int ind) throws SQLException
	{
		
PreparedStatement st= con.prepareStatement("select * FROM test WHERE index=?");
		
		st.setInt(1, ind);
		ResultSet rs= st.executeQuery();
		while(rs.next())
		{
			return( new DataTestRetrive(rs.getInt(1),rs.getString(2)));
			
			
		}
		st.close();
		return null;
		
	}

	

}
