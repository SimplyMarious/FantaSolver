package com.spme.fantasolver.dao;

public class UserDAOMySQL implements UserDAO{
    @Override
    public boolean signUp(String username, String password) {
        // Create a new user
        return false;
    }

    @Override
    public boolean signIn(String username, String password) {
        // Retrieve a user if it exists
        return false;
    }
}
