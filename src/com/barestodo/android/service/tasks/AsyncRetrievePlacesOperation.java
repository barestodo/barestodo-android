package com.barestodo.android.service.tasks;

import android.R;
import android.content.res.Resources;
import android.util.Log;
import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Circle;
import com.barestodo.android.place.Member;
import com.barestodo.android.place.Place;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.barestodo.android.R.string.*;
import static com.barestodo.android.repository.HttpOperationFactory.getGetOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * permet de retrouver la liste de Places d'un cercle
 */
public class AsyncRetrievePlacesOperation extends AbstractAsyncTask<String, Void, List<Place>> {

	public interface CirclePlacesReceiver extends OnAsynHttpError{
		void receivePlaces(List<Place> members);
	}

	private final Long circleId;
	private CirclePlacesReceiver receiver;

	public AsyncRetrievePlacesOperation(Long circleId,CirclePlacesReceiver receiver){
		this.circleId=circleId;
		this.receiver=receiver;
	}


	@Override
	List<Place> concreteOperation(String... params) throws Exception {
		List<Place> result=new ArrayList<Place>();
		StringBuilder urlResource=new StringBuilder();
		urlResource.append("circle/").append(circleId).append("/places");
		HttpGet httpGet = getGetOperation(urlResource.toString());

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
			
			DateTime eventTime = null;
			try {
				if(jsonPlace.has("eventTime")){
					eventTime= DateTime.parse(jsonPlace.getString("eventTime"));
				}
				Place place = new Place(jsonPlace.getString("id"),jsonPlace.getString("name"),jsonPlace.getString("location"),eventTime);
				result.add(place);
			} catch (Exception e) {
				e.printStackTrace();
				requestStatus=HttpStatus.SERVER_ERROR;
			}

		}
		return result;
	}

	@Override
	protected void onPostExecute(List<Place> result){
		if(hasFail()){
			receiver.onError(getRequestStatus());
		}else{
			receiver.receivePlaces(result);
		}
	}

}
