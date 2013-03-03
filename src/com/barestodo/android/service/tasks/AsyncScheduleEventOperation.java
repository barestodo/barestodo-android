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
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
public class AsyncScheduleEventOperation extends AbstractAsyncTask<String, Void, Boolean> {

    private String id;
    private String date;

    public AsyncScheduleEventOperation(String placeId, String scheduleDate){
        this.id=placeId;
        this.date=scheduleDate;
    }
    @Override
    protected Boolean doInBackground(String... strings) {

    	boolean schedulingResult = false;
        try {
            HttpPut httpPut = getPutOperation(constructSafeUrl(id, date));
            HttpResponse response = httpClient.execute(httpPut, localContext);
            checkResponseStatus(response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            String json = reader.readLine();
            Log.d("Task scheduling", json);
            JSONObject jsonResponse = new JSONObject(json);
            schedulingResult=true;
            
            //return new Place(jsonResponse.getString("id"),jsonResponse.getString("name"),jsonResponse.getString("location"));

        } catch (JSONException e) {
            throw new AsyncCallerServiceException(getErrorMessage(datas_corrupted));
        } catch (UnsupportedEncodingException e) {
            throw new AsyncCallerServiceException(getErrorMessage(datas_corrupted));
        } catch (ClientProtocolException e) {
            throw new AsyncCallerServiceException(getErrorMessage(connection_problem));
        } catch (IOException e) {
            throw new AsyncCallerServiceException(getErrorMessage(connection_problem));
        }
        
        return(schedulingResult);
    }



    private String constructSafeUrl(String placeId, String scheduleDate) throws UnsupportedEncodingException {
        StringBuilder url=new StringBuilder(placeId)
        .append("/plan/")
        .append(scheduleDate) ;

        String safeUrl=BASE_URL.concat("place/").concat(url.toString().replace(" ","%20"));

        return safeUrl;
    }
}
