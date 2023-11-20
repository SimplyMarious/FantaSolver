package com.spme.fantasolver.controllers;
import com.spme.fantasolver.entity.User;

public class AuthenticationManager {

    private static AuthenticationManager authenticationManager = null;

    private AuthenticationManager(){}

    public static AuthenticationManager getInstance(){
        if(authenticationManager == null){
            authenticationManager = new AuthenticationManager();
        }
        return authenticationManager;
    }

    private User user;

    public void signIn(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

}
