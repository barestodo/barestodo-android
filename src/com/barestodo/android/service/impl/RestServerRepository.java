package com.barestodo.android.service.impl;

import com.barestodo.android.place.Place;
import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.remote.RestResourceClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */
public class RestServerRepository implements IPlaceRepository {

    public static final RestServerRepository INSTANCE=new RestServerRepository();
    private RestResourceClient placeResourceClient= new RestResourceClient("place");


    private RestServerRepository(){

    }
    @Override
    public List<Place> getListPlace() {
        List<Place> places = new ArrayList<Place>();
        places=placeResourceClient.get();
        return places;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addPlace(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Place getPlaceById(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }



}
