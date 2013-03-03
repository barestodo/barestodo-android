package com.barestodo.android.security;

import android.accounts.*;
import com.barestodo.android.app.MyApplication;


public class IdentificationManager {

    public static final IdentificationManager INSTANCE=new IdentificationManager();
    private String token;

    private IdentificationManager(){
        retrieveToken();
    }

    public String getToken(){
        if(token==null || token.trim().equals("")){
              retrieveToken();
        }
        return token;

    }

    private void retrieveToken() {
       AccountManager accountManager = AccountManager.get(MyApplication.getAppContext());
       Account[] accounts = accountManager.getAccountsByType("com.google");
       Account myAccount= accounts[0];
       this.token=myAccount.name;
     }

    public String getEmail() {
        return token;
    }
}
