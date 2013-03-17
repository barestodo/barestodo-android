package com.barestodo.android.service.tasks;

import android.util.Log;
import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Place;
import com.barestodo.android.place.Circle;
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

/**
 * retrouve la liste de cercle de l'utilisateur
 */
public class AsyncRetrieveCirclesOperation extends AbstractAsyncTask<String, Void, List<Circle>> {
    public interface CirclesReceiver extends OnAsynHttpError{
        void receiveCircles(List<Circle> result);
    }

    private CirclesReceiver hascircles;

    public AsyncRetrieveCirclesOperation(CirclesReceiver hascircles) {
        super();
        this.hascircles=hascircles;
    }

    @Override
    List<Circle> concreteOperation(String... params) throws Exception {
        HttpGet httpGet = getGetOperation("circle");

        List<Circle> result=new ArrayList<Circle>();

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
                Circle circle = new Circle(jsonPlace.getLong("id"),jsonPlace.getString("name"));
                result.add(circle);
            }

        return result;
    }

    @Override
    protected void onPostExecute(List<Circle> result){
       hascircles.receiveCircles(result);
    }

}
