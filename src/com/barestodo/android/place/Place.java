package com.barestodo.android.place;

public class Place {
	private String id;
	private String label;
	
	public Place(String id, String label) {
        super();
        this.id = id;
        this.label = label;
    }
	
	
	public String getId(){
		return id;
	}
	
	public String getLabel(){
		return label;
	}
	
	public void setId(String newId){
		id = newId;
	}
	
	public void setLabel(String newLabel){
		label = newLabel;
	}
	
	
	 @Override
	 public String toString() {
	        return label;
	 }
	
}
