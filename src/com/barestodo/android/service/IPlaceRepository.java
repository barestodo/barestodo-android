package com.barestodo.android.service;

import java.util.List;

import com.barestodo.android.place.Place;

public interface IPlaceRepository {

	public abstract List<Place> getListPlace(Long circleId);
	
	public abstract Place addPlace(Place place);
	
	public abstract Place getPlaceById(String id);
	
	public abstract boolean scheduleEvent(String id, String date);
}