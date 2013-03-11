package com.barestodo.android.service.tasks;

import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Place;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static com.barestodo.android.R.string.connection_problem;
import static com.barestodo.android.R.string.datas_corrupted;
import static com.barestodo.android.repository.HttpOperationFactory.getGetOperation;
import static com.barestodo.android.repository.HttpOperationFactory.getPutOperation;


public class AsyncRetrieveCurrentUserNameOperation extends AbstractAsyncTask<String, Void, String> {

    private final UserNameReceiver receiver;

    public interface UserNameReceiver extends OnAsynHttpError{
        public void receiveUserName(String name);
    }


    public AsyncRetrieveCurrentUserNameOperation(UserNameReceiver receiver){
          this.receiver =receiver;
    }
    @Override
    protected String doInBackground(String... strings) {

        try {
            HttpGet httpRequest = getGetOperation("user/pseudo");
            HttpResponse response = httpClient.execute(httpRequest, localContext);
            checkResponseStatus(response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            return reader.readLine();

        } catch (UnsupportedEncodingException e) {
            throw new AsyncCallerServiceException(getErrorMessage(datas_corrupted));
        } catch (ClientProtocolException e) {
            throw new AsyncCallerServiceException(getErrorMessage(connection_problem));
        } catch (IOException e) {
            throw new AsyncCallerServiceException(getErrorMessage(connection_problem));
        }
    }


    @Override
    protected void onPostExecute(String pseudo){
        if(getRequestStatus()==null){
            receiver.receiveUserName(pseudo);
        }else{
            receiver.onError(getRequestStatus());
        }
    }


}
