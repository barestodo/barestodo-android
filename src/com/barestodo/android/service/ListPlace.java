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
		placeList.add(new Place("4","Quebecois"));
		placeList.add(new Place("5","Quebecois1"));
		placeList.add(new Place("6","Quebecois2"));
		placeList.add(new Place("7","Quebecois3"));
		placeList.add(new Place("8","Quebecois4"));
		placeList.add(new Place("9","Quebecois5"));
		placeList.add(new Place("10","Quebecois6"));
		placeList.add(new Place("11","Quebecois7"));
		placeList.add(new Place("12","Quebecois8"));
		placeList.add(new Place("13","Quebecois9"));
	}
	

	@Override
	public List<Place> getListPlace(){
		return (placeList);
	}
	
	public void addPlace(String name){
		placeList.add(new Place("1",name));
	}
	
	
	public Place getPlaceById(String id){
		return placeList.get(Integer.parseInt(id));
	}
		
}
