package com.barestodo.android.repository;

import com.barestodo.android.security.IdentificationManager;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

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
        httpGet.setHeader("ident", IdentificationManager.INSTANCE.getToken());
        return httpGet;
    }

    public static HttpPut getPutOperation(String resource){
        HttpPut httpPut = new HttpPut(BASE_URL.concat(resource));
        httpPut.setHeader("ident", IdentificationManager.INSTANCE.getToken());
        return httpPut;
    }
    
    public static HttpDelete getDeleteOperation(String resource){
    	HttpDelete httpDelete = new HttpDelete(BASE_URL.concat(resource));
    	httpDelete.setHeader("ident", IdentificationManager.INSTANCE.getToken());
        return httpDelete;
    }

    public static HttpPost getPostOperation(String resource){
        HttpPost httpPost = new HttpPost(BASE_URL.concat(resource));
        httpPost.setHeader("ident", IdentificationManager.INSTANCE.getToken());
        return httpPost;
    }


}
