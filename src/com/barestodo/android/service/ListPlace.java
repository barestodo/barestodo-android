package com.barestodo.android.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.barestodo.android.place.Place;

public class ListPlace implements IPlaceRepository {
	public final static ListPlace INSTANCE = new ListPlace();
	private static List<Place> placeList = new ArrayList<Place>();
	private ListPlace(){
		
	}
	static{
		placeList.add(new Place("0","Troll"));
		placeList.add(new Place("1","Renaissance"));
		placeList.add(new Place("2","Cantada"));
		placeList.add(new Place("3","Quebecois"));
	}
	

	@Override
	public List<Place> getListPlace(){
		return (placeList);
	}
	
	public void addPlace(String name){
		placeList.add(new Place("3","Quebecois"));
	}
	
	
	public Place getPlaceById(String id){
		return placeList.get(Integer.parseInt(id));
	}
	
	
	
	
	
	
	
	
	
	
}
