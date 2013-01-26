package com.barestodo.android.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.barestodo.android.place.Place;
import com.barestodo.android.service.IPlaceRepository;

public class ListPlace implements IPlaceRepository {
	public final static ListPlace INSTANCE = new ListPlace();
	private static List<Place> placeList = new ArrayList<Place>();
	private ListPlace(){
		
	}
	static{
		placeList.add(new Place("1","Troll","rue de cote"));
		placeList.add(new Place("2","Renaissance","la défense"));
		placeList.add(new Place("3","Cantada","somewhere" ));
		placeList.add(new Place("4","Quebecois","carriere"));
	}
	

	@Override
	public List<Place> getListPlace(){
		return (placeList);
	}
	
	public void addPlace(String name){
		placeList.add(new Place(String.valueOf(placeList.size()),name,"location"));
	}
	
	
	public Place getPlaceById(String id){
		return placeList.get(Integer.parseInt(id));
	}
		
}
