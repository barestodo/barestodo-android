package com.barestodo.android.service.tasks;

import com.barestodo.android.exception.AsyncCallerServiceException;
import com.barestodo.android.place.Circle;
import com.barestodo.android.place.Place;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.barestodo.android.R.string.connection_problem;
import static com.barestodo.android.R.string.datas_corrupted;
import static com.barestodo.android.repository.HttpOperationFactory.getGetOperation;
import static com.barestodo.android.repository.HttpOperationFactory.getPutOperation;

/**
 * enregistre l'utilisateur auprès du site,en lui assignant un pseudo
 */
public class AsyncSetCurrentUserNameOperation extends AbstractAsyncTask<String, Void, Void> {

    private final UserRegistrationReceiver receiver;

    public interface UserRegistrationReceiver extends OnAsynHttpError{
        public void isRegistered();
    }
    private String pseudo;

    public AsyncSetCurrentUserNameOperation(String pseudo,UserRegistrationReceiver receiver){
        this.pseudo=pseudo.replace(" ","").replace("\n","");
        this.receiver=receiver;
    }


    @Override
    Void concreteOperation(String... params) throws Exception {
        HttpPut httpRequest = getPutOperation("user/".concat(pseudo));
        HttpResponse response = httpClient.execute(httpRequest, localContext);
        checkResponseStatus(response.getStatusLine().getStatusCode());
        return Void.class.newInstance();
    }

    @Override
    protected void onPostExecute(Void response){
            receiver.isRegistered();
    }
}