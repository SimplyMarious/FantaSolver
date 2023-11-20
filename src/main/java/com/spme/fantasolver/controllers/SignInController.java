package com.spme.fantasolver.controllers;

import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.entity.User;
import com.spme.fantasolver.ui.HomeStage;

public class SignInController {

    private static SignInController signInController = null;

    private SignInController(){}

    public static SignInController getInstance(){
        if(signInController == null){
            signInController = new SignInController();
        }
        return signInController;
    }

    public void handleSignInOutcome(boolean signInOutcome){
        if(signInOutcome){
            User user = new User("TestUser");
            AuthenticationManager.getInstance().signIn(user);
            Team team = DAOFactory.getTeamDAO().retrieveTeam(user);
            if(team != null){
                user.setTeam(team);
                new HomeStage(true);
            }
            else{
                new HomeStage(false);
            }
        }
    }
}
