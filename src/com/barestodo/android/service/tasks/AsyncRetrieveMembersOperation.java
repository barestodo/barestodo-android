package com.barestodo.android.service.tasks;

import android.util.Log;
import com.barestodo.android.model.Member;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.barestodo.android.service.tasks.HttpOperationFactory.getGetOperation;

/**
 * retrouve la liste des memberes du cercle
 */
public class AsyncRetrieveMembersOperation extends AbstractAsyncTask<String, Void, List<Member>> {

    public interface CircleMembersReceiver extends OnAsynHttpError{
        void receiveMembers(List<Member> members);
    }

    private final Long circleId;
    private CircleMembersReceiver receiver;

    public AsyncRetrieveMembersOperation(CircleMembersReceiver receiver,Long circleId){
        this.circleId=circleId;
        this.receiver=receiver;
    }


    @Override
    List<Member> concreteOperation(String... params) throws Exception {
        List<Member> result=new ArrayList<Member>();
        StringBuilder urlResource=new StringBuilder();
        urlResource.append("circle/").append(circleId).append("/members");
        HttpGet httpGet = getGetOperation(urlResource.toString());

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
            Member member = new Member(jsonMember.getString("email"),jsonMember.getString("pseudo"));
            result.add(member);
        }
        return result;
    }

   @Override
    protected void onPostExecute(List<Member> result){
        if(hasFail()){
            receiver.onError(getRequestStatus());
        }else{
            receiver.receiveMembers(result);
        }
    }


}
