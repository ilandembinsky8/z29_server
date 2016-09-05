package tsofen.course.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*
 * Setting Up Your Class
 * 1.Add the @DatabaseTable annotation to the top of each class.
 *  You can also use @Entity.
 */
@DatabaseTable(tableName = "item")
public class Item extends Entity {
	/*
	 * 2.Add the @DatabaseField annotation right before each field to be
	 *  persisted. You can also use @Column and others.
	 */
	@DatabaseField(generatedId=true)
	private int Id;
	
	@DatabaseField()
	private String data;
	
	
	public void setId(int id) {
		Id = id;
	}
	/*
	 * 3.Add a no-argument constructor to each class 
	 * with at least package visibility.
	 */
	public Item() {
		super();
	}
	public Item(String data) {
		super();
		this.data = data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getId(){
		return this.Id;
	}
	
	public String getData(){
		return this.data;
	}
}
