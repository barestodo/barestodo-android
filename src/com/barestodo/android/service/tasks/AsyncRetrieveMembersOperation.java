package com.barestodo.android.service.tasks;

import android.util.Log;
import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Member;
import com.barestodo.android.place.Place;
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
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
public class AsyncRetrieveMembersOperation extends AbstractAsyncTask<String, Void, List<Member>> {


    private final Long circleId;

    public AsyncRetrieveMembersOperation(Long circleId){
        this.circleId=circleId;
    }
    @Override
    protected List<Member> doInBackground(String... strings) {
        List<Member> result=new ArrayList<Member>();
        StringBuilder urlResource=new StringBuilder();
        urlResource.append("circle/").append(circleId).append("/members");
        HttpGet httpGet = getGetOperation(urlResource.toString());

        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);

            checkResponseStatus(response.getStatusLine().getStatusCode());
        
            HttpEntity entity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            String json = reader.readLine();
            JSONObject jsonResponse = new JSONObject(json);

            JSONArray finalResult = jsonResponse.getJSONArray("members");
            Log.i("JSON", finalResult.toString());
            for(int index=0;index<finalResult.length();index++){
                JSONObject jsonMember = finalResult.getJSONObject(index);
                Member member = new Member(jsonMember.getString("pseudo"),jsonMember.getString("email"));
                result.add(member);
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