package com.barestodo.android.security;

import android.accounts.*;
import com.barestodo.android.app.MyApplication;


public class IdentificationManager {
    private boolean runOnVm =false;

    public static final IdentificationManager INSTANCE=new IdentificationManager();
    private String token;

    private IdentificationManager(){
        if(runOnVm){
            token="jean@bidule.net";
        }else{
            retrieveToken();
        }
    }

    public String getToken(){
        if(tokenIsNotSetted()){
              retrieveToken();
        }
        return token;
    }

    private boolean tokenIsNotSetted() {
        return token==null || token.trim().equals("");
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
