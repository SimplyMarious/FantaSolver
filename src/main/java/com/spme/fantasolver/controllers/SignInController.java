package com.spme.fantasolver.controllers;

import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.UserDAO;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.entity.User;
import com.spme.fantasolver.ui.HomeStage;
import com.spme.fantasolver.ui.SignInStage;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.spme.fantasolver.utility.Utility.checkStringValidity;

public class SignInController {

    private static SignInController signInController = null;
    private SignInStage signInStage;

    private SignInController(){}

    public static SignInController getInstance(){
        if(signInController == null){
            signInController = new SignInController();
        }
        return signInController;
    }

    public void handleInitialization(SignInStage signInStage) {
        this.signInStage = signInStage;

        try {
            signInStage.initializeStage();
        } catch (IOException e) {
            System.err.println("Error in reading FXML file.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    // TODO: create sign-up stage
    public void handlePressedSignupButton() {
    }

    public void handlePressedSigninButton(String username, String password) {
        UserDAO userDAO = DAOFactory.getUserDAO();
        if(userDAO.signIn(username, password))
            handleSignInOutcome(true);
        else
            signInStage.showFailedSignInLabel();
    }

    public void handleFieldChanged(TextField username, TextField password) {
        int minLength = 4;
        int maxLength = 20;

        boolean usernameValidity = checkStringValidity(username.toString(), minLength, maxLength);
        boolean passwordValidity = checkStringValidity(password.toString(), minLength, maxLength);

        if (usernameValidity && passwordValidity)
            if (signInStage.isSignInDisable())
                signInStage.enableSigninButton();
            else
            if (signInStage.isSignInEnable())
                signInStage.disableSigninButton();
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
