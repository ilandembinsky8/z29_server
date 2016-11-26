package com.mythings.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Test {

	public static void main(String[] args) {
		
		HashMap<Integer, String> hmap = new HashMap<Integer, String>();

	    /*Adding elements to HashMap*/
	    hmap.put(12, "Chaitanya");
	    hmap.put(2, "Rahul");
	    hmap.put(7, "Singh");
	    hmap.put(49, "Ajeet");
	    hmap.put(3, "Anuj");

	    Set<Entry<Integer, String>> set = hmap.entrySet();
	    Iterator<Entry<Integer, String>> iterator = set.iterator();
	    while(iterator.hasNext()) {
	    	Map.Entry<Integer,String> mentry = (Map.Entry<Integer,String>)iterator.next();
	    	System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
	        System.out.println(mentry.getValue());
	    }
		
	}

}
