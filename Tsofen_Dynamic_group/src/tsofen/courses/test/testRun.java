package tsofen.courses.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tsofen.course.db.DbHandler;
import tsofen.course.db.MyConnection;
import tsofen.course.models.Item;

public class testRun {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List items = new  ArrayList<Item>();
		
		MyConnection con = new MyConnection();
		// connection ormlite 
		DbHandler db=con.getHandler(); 
		
		Item item =new Item("15"); 
		
		
		// add item to table in database if it is not exist 
		db.item.createIfNotExists(item);
		
		//select from items where id=1 
		Item itm =(Item) db.item.queryForId(1); 
		
		
		// select from items where data="15"
		 items= (ArrayList)db.item.queryBuilder().where().eq("data","15").query();
		
	}

}
