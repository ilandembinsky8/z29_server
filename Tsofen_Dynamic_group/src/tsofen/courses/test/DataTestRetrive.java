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
	public void getDataFromDb(int ind)// throws SQLException
	{
		this.index=1;
		this.data="George";

	}

	public void getDataFromDb(Connection con,int ind) throws SQLException
	{
		
     PreparedStatement st= con.prepareStatement("select * FROM item WHERE id=?");
		
	st.setInt(1, ind);
	ResultSet rs= st.executeQuery();
		
		while(rs.next())
		{
			//return( new DataTestRetrive(rs.getInt(1),rs.getString(2)));
			this.index=rs.getInt(1);
		this.data=rs.getString(2);
	
		}
		st.close();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
}
