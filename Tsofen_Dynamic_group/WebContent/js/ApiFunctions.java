package tsofen.courses.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class ApiFunctions {
	/*public static List<Object[]> toList(ResultSet resultSet) throws SQLException{
		List<Object[]> records=new ArrayList<Object[]>();
		while(resultSet.next()){
		    int cols = resultSet.getMetaData().getColumnCount();
		    Object[] arr = new Object[cols];
		    for(int i=0; i<cols; i++)
		      arr[i] = resultSet.getObject(i+1);
		    
		    records.add(arr);
		
		}
		return records;
	}
	*/
	private static JSONArray toList(ResultSet resultSet) throws SQLException{
		
		JSONObject obj ;
		 JSONArray array=new JSONArray();
		while(resultSet.next()){
			
		    int cols = resultSet.getMetaData().getColumnCount();
		   obj = new JSONObject();
		    String colName;
		    for(int i=0; i<cols; i++){
		     colName=resultSet.getMetaData().getColumnName(i+1);
		     Object val=resultSet.getObject(i+1);
		     obj.put(colName, val);
		    }
		   
		    array.add(obj);
		   
		
		}
		return array;
	}
	
	
	public static JSONArray getQuery (Connection con,int ind) throws SQLException{
		DataTestRetrive data=new DataTestRetrive();
		ResultSet rs =data.getDataFromDb(con, ind);
		return toList(rs);
		
	}

}
