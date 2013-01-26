package com.barestodo.android.place;

public class Place {
	private String id;
	private String name;
	private String location;

	public Place(String id, String name,String location) {
        super();
        this.id = id;
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
