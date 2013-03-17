package com.barestodo.android.service.tasks;

import android.util.Log;
import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Place;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.barestodo.android.R.string.connection_problem;
import static com.barestodo.android.R.string.datas_corrupted;
import static com.barestodo.android.repository.HttpOperationFactory.getPutOperation;

/**
 * Crée une place
 */
public class AsyncCreatePlaceOperation extends AbstractAsyncTask<String, Void, Place> {

    private Place place;

    public AsyncCreatePlaceOperation(Place place){
        this.place=place;
    }

    @Override
    Place concreteOperation(String... params) throws Exception {
        HttpPut httpPut = getPutOperation(constructSafeUrl(place));
        HttpResponse response = httpClient.execute(httpPut, localContext);
        checkResponseStatus(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();

        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        String json = reader.readLine();
        JSONObject jsonResponse = new JSONObject(json);

        return new Place(jsonResponse.getString("id"),jsonResponse.getString("name"),jsonResponse.getString("location"));
    }


    private String constructSafeUrl(Place place) throws UnsupportedEncodingException {
        StringBuilder url=new StringBuilder(place.getName())
        .append("/")
        .append(place.getLocation()) ;

        String safeUrl="place/".concat(url.toString().replace(" ","%20"));

        return safeUrl;
    }
}
