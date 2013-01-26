package com.barestodo.android.service.remote;

import android.os.AsyncTask;
import android.util.Log;
import com.barestodo.android.place.Place;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 21:22
 * To change this template use File | Settings | File Templates.
 */
public class RestResourceClient<T> {

    private String ressource;
    private static final String BASE_URL="http://service.barestodo.cloudbees.net/rest/";

    public RestResourceClient(String ressource){
         this.ressource=ressource;
    }

    public List<Place> get(){
        List<Place> result=new ArrayList<Place>();

        try {
            AsyncRetrievePlacesOperation task= new AsyncRetrievePlacesOperation();
            result= task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }
}
