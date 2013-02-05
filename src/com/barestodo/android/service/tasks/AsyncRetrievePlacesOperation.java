package com.barestodo.android.service.tasks;

import android.R;
import android.content.res.Resources;
import android.util.Log;
import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Place;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.barestodo.android.R.string.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
public class AsyncRetrievePlacesOperation extends AbstractAsyncTask<String, Void, List<Place>> {

    @Override
    protected List<Place> doInBackground(String... strings) {
        List<Place> result=new ArrayList<Place>();
        HttpGet httpGet = new HttpGet(BASE_URL.concat("place"));

        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);

            checkResponseStatus(response.getStatusLine().getStatusCode());
        
            HttpEntity entity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            String json = reader.readLine();
            JSONObject jsonResponse = new JSONObject(json);

            JSONArray finalResult = jsonResponse.getJSONArray("places");
            Log.i("JSON", finalResult.toString());
            for(int index=0;index<finalResult.length();index++){
                JSONObject jsonPlace = finalResult.getJSONObject(index);
                Place place = new Place(jsonPlace.getString("id"),jsonPlace.getString("name"),jsonPlace.getString("location"));
                result.add(place);
            }
        } catch (JSONException e) {
            throw new AsyncCallerServiceException(getErrorMessage(datas_corrupted));
        } catch (UnsupportedEncodingException e) {
            throw new AsyncCallerServiceException(getErrorMessage(datas_corrupted));
        } catch (ClientProtocolException e) {
            throw new AsyncCallerServiceException(getErrorMessage(connection_problem));
        } catch (IOException e) {
            throw new AsyncCallerServiceException(getErrorMessage(connection_problem));
        }

        return result;  //To change body of implemented methods use File | Settings | File Templates.
    }



}
