package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.InternalException;
import com.spme.fantasolver.dao.UserDAO;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.entity.User;
import com.spme.fantasolver.ui.*;
import com.spme.fantasolver.utility.Notifier;

import java.io.IOException;
import java.util.logging.Logger;

import static com.spme.fantasolver.utility.Utility.checkStringValidity;


public class SignInController {

    private static SignInController signInController = null;
    private SignInStage signInStage;
    private StageFactory stageFactory;

    @Generated
    private SignInController(){}

    public static SignInController getInstance(){
        if(signInController == null){
            signInController = new SignInController();
        }
        return signInController;
    }

    @Generated
    public void setStageFactory(StageFactory factory){
        this.stageFactory = factory;
    }

    public void handleInitialization() {
        try {
            signInStage.initializeStage();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("SignInController");
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }
        signInStage.show();
    }

    @Generated
    public void setSignInStage(SignInStage signInStage) {
        this.signInStage = signInStage;
    }

    @Generated
    public void handlePressedSignUpButton() {
        stageFactory.createSignUpStage();
    }

    public void handlePressedSignInButton(String username, String password) {
        UserDAO userDAO = DAOFactory.getUserDAO();
        boolean signInOutcome = userDAO.signIn(username, password);
        handleSignInOutcome(signInOutcome);
    }

    public void handleFieldChanged(String username, String password) {
        int minLength = 4;
        int maxLength = 20;

        boolean usernameValidity = checkStringValidity(username, minLength, maxLength);
        boolean passwordValidity = checkStringValidity(password, minLength, maxLength);

        if (usernameValidity && passwordValidity) {
            if (signInStage.isSignInDisabled()) {
                signInStage.setSignInButtonAbility(true);
            }
        }
        else if (signInStage.isSignInEnabled()) {
           signInStage.setSignInButtonAbility(false);
        }
    }

    @Generated
    private void handleSignInOutcome(boolean signInOutcome){
        try{
            tryHandleSignInOutcome(signInOutcome);
        }
        catch (InternalException exception){
            Notifier.notifyError("Errore", "Errore imprevisto");
        }

    }

    @Generated
    private void tryHandleSignInOutcome(boolean signInOutcome) throws InternalException {
        if(signInOutcome){
            User user = new User(signInStage.getUsername());
            AuthenticationManager.getInstance().signIn(user);
            Team team = DAOFactory.getTeamDAO().retrieveTeam(user);
            if(team != null) {
                user.setTeam(team);
            }
        }
        else {
            signInStage.showFailedSignInLabel();
        }
        stageFactory.createHomeStage();
    }
}
