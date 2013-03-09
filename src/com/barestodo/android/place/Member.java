package com.barestodo.android.place;

public class Member {

	
	private String email ;
	private String pseudo;
	
    public String getEmail() {
        return email;
    }
    
    public String getPseudo() {
        return pseudo;
    }
	
    public Member(String email, String pseudo){
    	this.email = email;
    	this.pseudo = pseudo;
    }
}
