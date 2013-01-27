package com.barestodo.android.service.impl;

import android.util.Log;
import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Place;
import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.tasks.AsyncCreatePlaceOperation;
import com.barestodo.android.service.tasks.AsyncRetrievePlacesOperation;

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
            places= task.execute().get();
        } catch (InterruptedException e){
        	throw new AsyncCallerServiceException("Erreur during get place list",e);
        }catch(ExecutionException e){
            throw new AsyncCallerServiceException("Erreur during get place list",e);
        }

        return places;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Place addPlace(Place place) {
        try{
            Log.d("RestServerRepo","Create place init");
            AsyncCreatePlaceOperation operation=new AsyncCreatePlaceOperation(place);
            return operation.execute().get();
        }catch(Exception e){
            throw new AsyncCallerServiceException("Erreur during place creation",e);
        }
    }

    @Override
    public Place getPlaceById(String id) {
       throw new UnsupportedOperationException("Not yet implemented");
    }



}
