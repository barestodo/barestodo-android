package com.barestodo.android.service.tasks;

import android.content.res.Resources;
import android.os.AsyncTask;
import com.barestodo.android.exception.AsyncCallerServiceException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import static com.barestodo.android.R.string.*;
import static com.barestodo.android.repository.HttpOperationFactory.getGetOperation;


public abstract class AbstractAsyncTask<Params,Progress,Results> extends AsyncTask<Params,Progress,Results> {

    public interface OnAsynHttpError {
        void onError(HttpStatus status);
    }

    private HttpStatus requestStatus;

    protected HttpClient httpClient = new DefaultHttpClient();
    protected HttpContext localContext = new BasicHttpContext();

    public HttpStatus getRequestStatus(){
        return requestStatus;
    }

    public boolean hasFail() {
        return requestStatus!=HttpStatus.OK;
    }

    protected void checkResponseStatus(int statusCode){
        switch(statusCode){
            case 200:
            case 201:
            case 202:
            case 204:
                requestStatus=HttpStatus.OK;break;
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
            default:
                requestStatus=HttpStatus.SERVER_ERROR;
        }
    }

    @Override
    protected Results doInBackground(Params... strings) {

        try {
            return concreteOperation(strings);
        } catch (UnsupportedEncodingException e) {
            requestStatus=HttpStatus.CLIENT_ERROR;
        } catch (ClientProtocolException e) {
            requestStatus=HttpStatus.CONNECTION_LOST;
        }catch (IOException e) {
            requestStatus=HttpStatus.CONNECTION_LOST;
        }catch(Exception e){
            requestStatus=HttpStatus.CLIENT_ERROR;
        }
        return null;
    }

    abstract Results concreteOperation(Params... params) throws Exception;


    protected String getErrorMessage(int messageId) {
        return Resources.getSystem().getString(messageId);
    }


}
