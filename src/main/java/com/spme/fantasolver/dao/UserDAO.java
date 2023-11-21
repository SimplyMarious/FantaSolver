package com.spme.fantasolver.dao;

public interface UserDAO {
    boolean signUp(String username, String password);
    boolean signIn(String username, String password);
}
