package com.barestodo.android.service;

import java.util.List;

import com.barestodo.android.place.Place;

public interface IPlaceRepository {

	public abstract List<Place> getListPlace();
	
	public abstract void addPlace(String name);
	
	
	public abstract Place getPlaceById(String id);
}