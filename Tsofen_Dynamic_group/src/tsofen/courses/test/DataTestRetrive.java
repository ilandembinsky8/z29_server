package tsofen.courses.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tsofen.course.db.MyConnection;

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


	public ResultSet getDataFromDb(Connection con) throws SQLException
	{
		
	     PreparedStatement st= con.prepareStatement("SELECT * FROM Tsofen29Database.item");			
		 ResultSet rs= st.executeQuery();	
		 return rs;
		 
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
