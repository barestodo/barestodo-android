package com.barestodo.android.security;

import android.accounts.*;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 29/01/13
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public class OAuthManager {

    public static final OAuthManager INSTANCE=new OAuthManager();
    private String token;

    private OAuthManager(){

    }

    public String getToken(){
        if(token==null || token.isEmpty()){
              // token=
        }
        return token;
    }


    public void authenticate(){
        Bundle options = new Bundle();
      //  AccountManager accountManager = AccountManager.get(getApplicationContext());
      //  Account[] accounts = accountManager.getAccountsByType("com.google");
 /*       Toast.makeText(getBaseContext(), accounts[0].toString(), 20);
        Account myAccount_= accounts[0];
        AccountManagerFuture<Bundle> authToken;
        android.os.Handler.Callback onErrorHandler= new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                return false;
            }
        };
        authToken = accountManager.getAuthToken(myAccount_,//
                "Manage your tasks",
                options,
                MainActivity.this,
                new OnTokenAcquired(),
                new android.os.Handler(onErrorHandler));
   */
    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {

        @Override
        public void run(AccountManagerFuture<Bundle> bundleAccountManagerFuture) {
            // Get the result of the operation from the AccountManagerFuture.
            Bundle bundle = null;
            try {
                bundle = bundleAccountManagerFuture.getResult();
                // The token is a named value in the bundle. The name of the value
                // is stored in the constant AccountManager.KEY_AUTHTOKEN.
                String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
            } catch (OperationCanceledException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (AuthenticatorException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }


        }
    }
}
