package com.barestodo.android.service.tasks;

import android.content.res.Resources;
import android.os.AsyncTask;
import com.barestodo.android.exception.AsyncCallerServiceException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import static com.barestodo.android.R.string.*;


public abstract class AbstractAsyncTask<Params,Progress,Results> extends AsyncTask<Params,Progress,Results> {

    public interface OnAsynHttpError {
        void onError(HttpStatus status);
    }

    private HttpStatus requestStatus;

    protected HttpClient httpClient = new DefaultHttpClient();
    protected HttpContext localContext = new BasicHttpContext();
    protected static final String BASE_URL="http://service.barestodo.cloudbees.net/rest/";

    public HttpStatus getRequestStatus(){
        return requestStatus;
    }
    public boolean isOk() {
        return requestStatus==null;
    }

    protected void checkResponseStatus(int statusCode){
        switch(statusCode){
            case 200:
                requestStatus=null;break;
            case 400:
                requestStatus=HttpStatus.BAD_REQUEST;
                break;
            case 401:
            case 403:
                requestStatus=HttpStatus.FORBIDEN;
                break;
            case 404:
                requestStatus=HttpStatus.NOT_FOUND;
                break;
            case 500:
                requestStatus=HttpStatus.SERVER_ERROR;
                break;
        }
    }


    protected String getErrorMessage(int messageId) {
        return Resources.getSystem().getString(messageId);
    }


}
