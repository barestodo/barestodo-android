package com.barestodo.android.service.tasks;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;

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