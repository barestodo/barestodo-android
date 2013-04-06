package com.barestodo.android.service.tasks;

import com.barestodo.android.model.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static com.barestodo.android.service.tasks.HttpOperationFactory.getPostOperation;

/**
 * Inviter un membre dans le cercle
 */
public class AsyncInvitePeopleOnCircleOperation extends AbstractAsyncTask<String, Void, User> {


    public interface UserReceiver extends OnAsynHttpError{
        void receiveCompleteUser(User user);
    }

    private UserReceiver circleReceiver;
    private final String email;
    private final Long circleId;

    public AsyncInvitePeopleOnCircleOperation(String email,Long circleId, UserReceiver receiver){
        this.circleReceiver =receiver;
        this.email =email;
        this.circleId=circleId;
    }


    @Override
    public User concreteOperation(String... params) throws Exception {
        HttpPost httpPost = getPostOperation(constructSafeUrl(circleId, email));
        HttpResponse response = httpClient.execute(httpPost, localContext);
        checkResponseStatus(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();

        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        String json = reader.readLine();
        JSONObject jsonResponse = new JSONObject(json);
        return new User(jsonResponse.getString("email"),jsonResponse.getString("pseudo"));
    }

    @Override
    protected void onPostExecute(User result){
        if(hasFail()){
            circleReceiver.onError(getRequestStatus());
        }else{
            circleReceiver.receiveCompleteUser(result);
        }
    }

    private String constructSafeUrl(Long circleId,String email) throws UnsupportedEncodingException {
        StringBuilder stringBuilder=new StringBuilder("circle/").append(circleId).append("/invite/").append(email);
        String safeUrl=stringBuilder.toString().replace(" ", "%20");
        return safeUrl;
    }
}
