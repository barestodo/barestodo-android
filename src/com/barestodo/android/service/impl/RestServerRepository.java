package com.barestodo.android.service.impl;

import android.os.AsyncTask;
import android.util.Log;
import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Place;
import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.tasks.AsyncCreatePlaceOperation;
import com.barestodo.android.service.tasks.AsyncRetrievePlacesOperation;
import com.barestodo.android.service.tasks.AsyncScheduleEventOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */
public class RestServerRepository implements IPlaceRepository {

    public static final RestServerRepository INSTANCE=new RestServerRepository();


    private RestServerRepository(){

    }
    @Override
    public List<Place> getListPlace() {
        List<Place> places = new ArrayList<Place>();
        try {
            AsyncRetrievePlacesOperation task= new AsyncRetrievePlacesOperation();
            String callback;
            places= task.execute().get();
        } catch (InterruptedException e){
        	throw new AsyncCallerServiceException("aborted",e);
        }catch(ExecutionException e){
            throw new AsyncCallerServiceException("aborted",e);
        }

        return places;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Place addPlace(Place place) {
        try{
            Log.d("RestServerRepo","Create place init");
            AsyncCreatePlaceOperation operation=new AsyncCreatePlaceOperation(place);
            AsyncTask<String,Void,Place> task = operation.execute();
            return task.get();
        }catch(Exception e){
            throw new AsyncCallerServiceException("aborted",e);
        }
    }

    @Override
    public Place getPlaceById(String id) {
       throw new UnsupportedOperationException("Not yet implemented");
    }
    
	@Override
	public boolean scheduleEvent(String id, String date) {
		// TODO Auto-generated method stub	
		try{
            Log.d("RestServerRepo","Schedule event");
            AsyncScheduleEventOperation operation=new AsyncScheduleEventOperation(id, date);
            AsyncTask<String,Void,Boolean> task = operation.execute();
            return task.get();
        }catch(Exception e){
            throw new AsyncCallerServiceException("aborted",e);
        }
	}



}
