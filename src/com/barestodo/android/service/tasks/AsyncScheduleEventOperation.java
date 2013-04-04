package com.barestodo.android.service.tasks;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static com.barestodo.android.repository.HttpOperationFactory.getPutOperation;

/**
 * planifie un événement pur une date donnée
 */
public class AsyncScheduleEventOperation extends AbstractAsyncTask<String, Void, Boolean> {

    private String id;
    private String date;

    public AsyncScheduleEventOperation(String placeId, String scheduleDate){
        this.id=placeId;
        this.date=scheduleDate;
    }


    @Override
    Boolean concreteOperation(String... params) throws Exception {
        boolean schedulingResult = true;
        HttpPut httpPut = getPutOperation(constructSafeUrl(id, date));
        HttpResponse response = httpClient.execute(httpPut, localContext);
        checkResponseStatus(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();

        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        String json = reader.readLine();
        Log.d("Task scheduling", json);
        JSONObject jsonResponse = new JSONObject(json);
        if(hasFail()){
            schedulingResult=false;
        }
        return(schedulingResult);
    }

    private String constructSafeUrl(String placeId, String scheduleDate) throws UnsupportedEncodingException {
        StringBuilder url=new StringBuilder(placeId)
        .append("/plan/")
        .append(scheduleDate) ;

        String safeUrl="place/".concat(url.toString().replace(" ","%20"));

        return safeUrl;
    }
}
