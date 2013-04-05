package com.barestodo.android.service.tasks;

import static com.barestodo.android.repository.HttpOperationFactory.getPostOperation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.joda.time.DateTime;
import org.json.JSONObject;

import android.util.Log;

import com.barestodo.android.model.Place;

public class AsyncSchedulePlaceOperation extends AbstractAsyncTask<String, Void, Place> {


	private String placeId;
	private String placeScheduleDate;

	public AsyncSchedulePlaceOperation(String placeId, DateTime placeScheduleDateTime){
		this.placeId=placeId;
		this.placeScheduleDate = placeScheduleDateTime.toString();
	}


	@Override
	Place concreteOperation(String... params) throws Exception {
		HttpPost httpPost = getPostOperation(constructSafeUrl());
		HttpResponse response = httpClient.execute(httpPost, localContext);
		checkResponseStatus(response.getStatusLine().getStatusCode());
		HttpEntity entity = response.getEntity();

		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
		String json = reader.readLine();
		JSONObject jsonResponse = new JSONObject(json);
		
		return null;
	}

	@Override
	protected void onPostExecute(Place result){
		if(hasFail()){
		}
	}
	
	private String constructSafeUrl() throws UnsupportedEncodingException {
		
		String safeUrl="place/".concat(placeId).concat("/plan/").concat(placeScheduleDate).replace(" ","%20");
		Log.d("safeUrl schedule", safeUrl);
		return safeUrl;
	}

}
