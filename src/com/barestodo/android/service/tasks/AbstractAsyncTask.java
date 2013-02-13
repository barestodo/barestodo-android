package com.barestodo.android.service.tasks;

import android.content.res.Resources;
import android.os.AsyncTask;
import com.barestodo.android.exception.AsyncCallerServiceException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import static com.barestodo.android.R.string.*;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 23:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAsyncTask<Params,Progress,Results> extends AsyncTask<Params,Progress,Results> {

    protected HttpClient httpClient = new DefaultHttpClient();
    protected HttpContext localContext = new BasicHttpContext();
    protected static final String BASE_URL="http://service.barestodo.cloudbees.net/rest/";

    protected void checkResponseStatus(int statusCode){
        switch(statusCode){
            case 200: return;
            case 400:
                throw new AsyncCallerServiceException(getErrorMessage(invalid_request));
            case 401:
            case 403:
                throw new AsyncCallerServiceException(getErrorMessage(authentication_problem));
            case 404:
                throw new AsyncCallerServiceException(getErrorMessage(not_found));
            case 500:
                throw new AsyncCallerServiceException(getErrorMessage(service_unavailable));
        }
    }


    protected String getErrorMessage(int messageId) {
        return Resources.getSystem().getString(messageId);
    }


}
