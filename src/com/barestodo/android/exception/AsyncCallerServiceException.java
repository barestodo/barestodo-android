package com.barestodo.android.exception;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 27/01/13
 * Time: 00:17
 * To change this template use File | Settings | File Templates.
 */
public class AsyncCallerServiceException extends RuntimeException{

    public AsyncCallerServiceException(String message){
        super(message);
    }

    public AsyncCallerServiceException(String message,Throwable e){
        super(message,e);
    }

}
