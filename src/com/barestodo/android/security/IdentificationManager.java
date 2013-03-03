package com.barestodo.android.security;

import android.accounts.*;
import com.barestodo.android.app.MyApplication;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 29/01/13
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public class IdentificationManager {

    public static final IdentificationManager INSTANCE=new IdentificationManager();
    private String token;

    private IdentificationManager(){

    }

    public String getToken(){
        /*if(token==null || token.isEmpty()){
              retrieveToken();
        }
        
        return token;*/
    	return "aricdestroy@gmail.com";
        
    }

    private void retrieveToken() {
       AccountManager accountManager = AccountManager.get(MyApplication.getAppContext());
       Account[] accounts = accountManager.getAccountsByType("com.google");
       Account myAccount= accounts[0];
       this.token=myAccount.name;
     }

}
