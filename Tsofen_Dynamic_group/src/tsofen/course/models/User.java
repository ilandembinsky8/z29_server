package tsofen.course.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*
 * Setting Up Your Class
 * 1.Add the @DatabaseTable annotation to the top of each class.
 *  You can also use @Entity.
 */
@DatabaseTable(tableName = "users")
public class User extends Entity {
	/*
	 * 2.Add the @DatabaseField annotation right before each field to be
	 *  persisted. You can also use @Column and others.
	 */
	@DatabaseField(generatedId=true)
	private int id;
	
	@DatabaseField()
	private String name;
	
	@DatabaseField()
	private String pass;
	
	@DatabaseField()
	private String email;
	
	
	/*
	 * 3.Add a no-argument constructor to each class 
	 * with at least package visibility.
	 */
	public User() {
		super();
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}



}

	