package com.spme.fantasolver.controllers;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.UserDAO;
import com.spme.fantasolver.ui.SignUpStage;
import java.io.IOException;
import java.util.logging.Logger;

import static com.spme.fantasolver.utility.Utility.checkStringValidity;

public class SignUpController {

    private static SignUpController signUpController = null;
    private SignUpStage signUpStage = null;
    private SignUpController(){}

    public static SignUpController getInstance(){
        if (signUpController == null)
            signUpController = new SignUpController();
        return signUpController;
    }

    public void handleInitialization(SignUpStage signUpStage) {
        this.signUpStage = signUpStage;

        try {
            signUpStage.initializeStage();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("SignUpController");
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }
    }

    public void setSignUpStage(SignUpStage signUpStage) {
        this.signUpStage = signUpStage;
    }

    public void handleFieldChanged(String username, String password) {
        int minLength = 4;
        int maxLength = 20;

        boolean usernameValidity = checkStringValidity(username, minLength, maxLength);
        boolean passwordValidity = checkStringValidity(password, minLength, maxLength);

        if (usernameValidity && passwordValidity) {
            if (signUpStage.isSignUpDisable()) {
                signUpStage.enableSignUpButton();
            }
        }
        else if (signUpStage.isSignUpEnable()) {
            signUpStage.disableSignUpButton();
        }
    }

    public void handlePressedSignUpButton(String username, String password) {
        UserDAO userDAO = DAOFactory.getUserDAO();
        if(userDAO.signUp(username, password)) {
            signUpStage.showSuccessfulSignUp();
        }
        else {
            signUpStage.showFailedSignUp();
        }
    }
}
