package tsofen.course.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "item")
public class Item extends Entity {
	
	@DatabaseField(generatedId=true)
	private int Id;
	
	@DatabaseField()
	private String data;
	
	
	public void setId(int id) {
		Id = id;
	}
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
