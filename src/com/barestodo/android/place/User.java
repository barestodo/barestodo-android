package com.barestodo.android.place;


public class User {

    private String pseudo;
    private String email;

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


}
