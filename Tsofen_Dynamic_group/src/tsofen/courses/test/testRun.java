package tsofen.courses.test;

import java.sql.SQLException;

import tsofen.course.db.DbHandler;
import tsofen.course.db.MyConnection;
import tsofen.course.models.Item;

public class testRun {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		MyConnection con = new MyConnection();
		DbHandler db=con.getHandler(); 
		Item item =new Item("15"); 
		db.item.createIfNotExists(item); 
		
	}

}
