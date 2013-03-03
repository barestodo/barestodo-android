package com.barestodo.android.repository;

import org.apache.http.client.methods.HttpGet;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 03/03/13
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public abstract class HttpOperationFactory {

    protected static final String BASE_URL="http://service.barestodo.cloudbees.net/rest/";

    public static HttpGet getGetOperation(String resource){
        HttpGet httpGet = new HttpGet(BASE_URL.concat(resource));
        httpGet.setHeader("ident","fab.maury@gmail.com");
        return httpGet;
    }
}
