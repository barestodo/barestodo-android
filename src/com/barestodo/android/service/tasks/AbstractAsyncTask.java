package com.barestodo.android.service.tasks;

import android.os.AsyncTask;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 26/01/13
 * Time: 23:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAsyncTask<A,B,C> extends AsyncTask<A, B, C> {

    protected HttpClient httpClient = new DefaultHttpClient();
    protected HttpContext localContext = new BasicHttpContext();
    protected static final String BASE_URL="http://service.barestodo.cloudbees.net/rest/";
}
