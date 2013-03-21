package com.barestodo.android.service.tasks;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.barestodo.android.repository.HttpOperationFactory.getGetOperation;


public class AsyncRetrieveCurrentUserNameOperation extends AbstractAsyncTask<String, Void, String> {

    private final UserNameReceiver receiver;

    public interface UserNameReceiver extends OnAsynHttpError{
        public void receiveUserName(String name);
    }


    public AsyncRetrieveCurrentUserNameOperation(UserNameReceiver receiver){
          this.receiver =receiver;
    }


    @Override
    String concreteOperation(String... params) throws Exception {
        HttpGet httpRequest = getGetOperation("user/pseudo");
        HttpResponse response = httpClient.execute(httpRequest, localContext);
        checkResponseStatus(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();

        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        return reader.readLine();
    }


    @Override
    protected void onPostExecute(String pseudo){
        if(hasFail()){
            receiver.onError(getRequestStatus());
        }else{
            receiver.receiveUserName(pseudo);
        }
    }


}
