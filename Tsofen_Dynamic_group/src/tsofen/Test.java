package tsofen;

import java.sql.ResultSet;
import java.sql.SQLException;

import tsofen.course.db.MyConnection;
import tsofen.courses.test.DataTestRetrive;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		MyConnection con = new MyConnection();
		DataTestRetrive data  = new DataTestRetrive();
		ResultSet s = data.getDataFromDb(con.getCon());
		System.out.println("hello");
	}

}
