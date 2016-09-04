package tsofen.course.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import tsofen.course.models.*;

public class DbHandler {

	private ConnectionSource conOrm;

	public Dao<Item,Integer> item;


	
	public DbHandler(String url, String username, String password) {
		try {
			conOrm = new JdbcConnectionSource(url,username,password);
			initializeDao();
			createAllTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initializeDao() throws Exception {
		item = DaoManager.createDao(conOrm,Item.class);

	}

	/**
	 * creates all the tables using ORM, it also drops the tables first
	 * 
	 * @throws Exception
	 */
	public void createAllTables() throws Exception {
		TableUtils.dropTable(conOrm, Item.class,true); 
		TableUtils.createTable(conOrm,Item.class);


	}
}
