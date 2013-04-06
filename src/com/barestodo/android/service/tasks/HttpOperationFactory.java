package com.barestodo.android.service.tasks;

import com.barestodo.android.security.IdentificationManager;
import org.apache.http.client.methods.*;


public abstract class HttpOperationFactory {


    protected static final String BASE_URL="http://service.barestodo.cloudbees.net/rest/";


    public static HttpGet getGetOperation(String resource){
        HttpGet httpRequest = new HttpGet(BASE_URL.concat(resource));
        addHeaders(httpRequest);
        return httpRequest;
    }


    public static HttpPut getPutOperation(String resource){
        HttpPut httpRequest = new HttpPut(BASE_URL.concat(resource));
        addHeaders(httpRequest);
        return httpRequest;
    }
    
    public static HttpDelete getDeleteOperation(String resource){
    	HttpDelete httpRequest = new HttpDelete(BASE_URL.concat(resource));
        addHeaders(httpRequest);
        return httpRequest;
    }

    public static HttpPost getPostOperation(String resource){
        HttpPost httpRequest = new HttpPost(BASE_URL.concat(resource));
        addHeaders(httpRequest);
        return httpRequest;
    }

    private static void addHeaders(HttpRequestBase httpRequest) {
        httpRequest.setHeader("x-token", IdentificationManager.INSTANCE.getToken());
        httpRequest.setHeader("x-os","android:"+android.os.Build.VERSION.RELEASE);
    }


}
