package com.barestodo.android.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.barestodo.android.place.Place;
import com.barestodo.android.service.IPlaceRepository;

public class StubPlaceRepository implements IPlaceRepository {
	public final static StubPlaceRepository INSTANCE = new StubPlaceRepository();
	private static List<Place> placeList = new ArrayList<Place>();
	private StubPlaceRepository(){
		
	}
	static{
		placeList.add(new Place("1","Troll","rue de cote"));
		placeList.add(new Place("2","Renaissance","la défense"));
		placeList.add(new Place("3","Cantada","somewhere" ));
		placeList.add(new Place("4","Quebecois","carriere"));
	}
	

	@Override
	public List<Place> getListPlace(Long circleId){
		return (placeList);
	}
	
	public Place addPlace(Place place){
        place = new Place(String.valueOf(placeList.size()), place.getName(), place.getLocation());
        placeList.add(place);
        return place;
	}
	
	
	public Place getPlaceById(String id){
		return placeList.get(Integer.parseInt(id));
	}

	@Override
	public boolean scheduleEvent(String id, String date) {
		// TODO Auto-generated method stub
		return true;
	}
		
}
