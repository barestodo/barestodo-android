package com.barestodo.android.place;

import java.io.Serializable;

public class Place implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3490633316443935678L;
	
	private String id;
	private String name;
	private String location;

	private Place(){
		//needed for serializations
	}
	public Place(String id, String name,String location) {
        super();
        this.id = id;
        this.name = name;
        this.location=location;
    }

    public Place(String name,String location) {
        super();
        this.name = name;
        this.location=location;
    }

	
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}

    public String getLocation(){
        return location;
    }

	 @Override
	 public String toString() {
	        return name;
	 }
	
}
