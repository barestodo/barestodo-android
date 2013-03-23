package com.barestodo.android.service.tasks;

import android.widget.Toast;

import com.barestodo.android.place.Place;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.barestodo.android.repository.HttpOperationFactory.getDeleteOperation;

/**
 * Crée une place
 */
public class AsyncDeletePlaceOperation extends AbstractAsyncTask<String, Void, Place> {


	private String placeId;

	public AsyncDeletePlaceOperation(String placeId){
		this.placeId=placeId;
	}


	@Override
	Place concreteOperation(String... params) throws Exception {
		HttpDelete httpDelete = getDeleteOperation(constructSafeUrl());
		HttpResponse response = httpClient.execute(httpDelete, localContext);
		checkResponseStatus(response.getStatusLine().getStatusCode());
		HttpEntity entity = response.getEntity();

		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
		String json = reader.readLine();
		JSONObject jsonResponse = new JSONObject(json);

		return new Place(jsonResponse.getString("id"),jsonResponse.getString("name"),jsonResponse.getString("location"));
	}

	@Override
	protected void onPostExecute(Place result){
		if(hasFail()){
		}
	}

	private String constructSafeUrl() throws UnsupportedEncodingException {
		String safeUrl="place/".concat(placeId).replace(" ","%20");
		return safeUrl;
	}
}      // /rest/circle/:circleId/place/:name/:location
