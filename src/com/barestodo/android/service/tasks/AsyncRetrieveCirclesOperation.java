package com.barestodo.android.service.tasks;

import android.util.Log;
import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Place;
import com.barestodo.android.place.PlaceList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.barestodo.android.R.string.connection_problem;
import static com.barestodo.android.R.string.datas_corrupted;
import static com.barestodo.android.repository.HttpOperationFactory.getGetOperation;


public class AsyncRetrieveCirclesOperation extends AbstractAsyncTask<String, Void, List<PlaceList>> {
    public interface HasCircles {

        void update(List<PlaceList> result);
    }
    private HasCircles hascircles;

    public AsyncRetrieveCirclesOperation(HasCircles hascircles) {
        super();
        this.hascircles=hascircles;
    }

    @Override
    protected List<PlaceList> doInBackground(String... strings) {
        HttpGet httpGet = getGetOperation("circle");

        List<PlaceList> result=new ArrayList<PlaceList>();
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);

            checkResponseStatus(response.getStatusLine().getStatusCode());
        
            HttpEntity entity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            String json = reader.readLine();
            JSONObject jsonResponse = new JSONObject(json);

            JSONArray finalResult = jsonResponse.getJSONArray("Circles");
            Log.i("JSON", finalResult.toString());
            for(int index=0;index<finalResult.length();index++){
                JSONObject jsonPlace = finalResult.getJSONObject(index);
                PlaceList circle = new PlaceList(jsonPlace.getLong("id"),jsonPlace.getString("name"));
                result.add(circle);
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
    @Override
    protected void onPostExecute(List<PlaceList> result){
       // hascircles.update(result);
    }

}
