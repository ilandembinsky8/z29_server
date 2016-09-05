package tsofen.courses.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import tsofen.course.db.DbHandler;
import tsofen.course.db.MyConnection;
import tsofen.course.models.Item;
import tsofen.course.models.User;

public class testRun {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		List<Object> obj = new  ArrayList<Object>();
		
		MyConnection con = new MyConnection();
		// connection ormlite 
		DbHandler db=con.getHandler(); 
		
		Item item =new Item("15"); 
		
		User user1= new User(); 
		user1.setEmail("a@a");
		user1.setName("Ahmad");
		user1.setPass("123123");
		
		db.user.create(user1);
		User u = db.user.queryForId(1);
		
		
		// add item to table in database if it is not exist 
		db.item.create(item);
		db.item.create(item);
		db.item.create(item);
		db.item.create(item);

		
		//select from items where id=1 
		Item itm =(Item) db.item.queryForId(1); 
		
		
		// select from items where data="15"
		 obj= (List)db.item.queryBuilder().where().eq("data","15").query();
		Gson gson = new Gson();
		 String jsonArr = gson.toJson(obj);
		 System.out.println(jsonArr);
		 
		User user= new User(); 
		user.setEmail("a@a");
		user.setName("Ahmad");
		user.setPass("123123");
		
		db.user.create(user); 
		db.user.create(user); 
		db.user.create(user); 

		 obj= (List)db.user.queryBuilder().where().eq("name","ahmad").query();
		 String jsonArr1 = gson.toJson(obj);
		 System.out.println(jsonArr);
		 System.out.println(jsonArr);

		 

		
	}

}
