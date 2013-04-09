package com.barestodo.android.model;


import java.io.Serializable;

public class User implements Serializable{

    public static final User EMPTY=new User();

    private String pseudo;
    private String email;

    private User(){
        //for be serializable
    }
    public User(String email,String pseudo){
        this.pseudo=pseudo;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public String getPseudo() {
        return pseudo;
    }


    public String getLabel() {
        if (pseudo == null) {
            return getUserNameFromEmail();
        }
        return pseudo;
    }

    public String getUserNameFromEmail() {
        if(email!=null){
            return email.substring(0,email.lastIndexOf("@"));
        }
        return "invalid user";
    }
}
