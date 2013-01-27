package com.barestodo.android.service.tasks;

import android.util.Log;
import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Place;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
public class AsyncCreatePlaceOperation extends AbstractAsyncTask<String, Void, Place> {

    private Place place;

    public AsyncCreatePlaceOperation(Place place){
        this.place=place;
    }
    @Override
    protected Place doInBackground(String... strings) {

        try {
            HttpPut httpPut = new HttpPut(constructSafeUrl(place));
            HttpResponse response = httpClient.execute(httpPut, localContext);
            //TODO checker response status
            HttpEntity entity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            String json = reader.readLine();
            JSONObject jsonResponse = new JSONObject(json);

            return new Place(jsonResponse.getString("id"),jsonResponse.getString("name"),jsonResponse.getString("location"));

        } catch (Exception e) {
            throw new AsyncCallerServiceException("Error during place creation",e);
        }

        //To change body of implemented methods use File | Settings | File Templates.
    }

    private String constructSafeUrl(Place place) throws UnsupportedEncodingException {
        StringBuilder url=new StringBuilder(place.getName())
        .append("/")
        .append(place.getLocation()) ;

        String safeUrl=BASE_URL.concat("place/").concat(url.toString().replace(" ","%20"));

        return safeUrl;
    }
}
