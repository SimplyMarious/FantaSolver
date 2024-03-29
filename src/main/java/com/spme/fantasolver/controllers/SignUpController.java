package com.spme.fantasolver.controllers;
import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.UserDAO;
import com.spme.fantasolver.ui.AbstractSignUpStage;
import java.io.IOException;
import java.util.logging.Logger;

import static com.spme.fantasolver.utility.Utility.checkStringValidity;

public class SignUpController {

    private static SignUpController signUpController = null;
    private AbstractSignUpStage signUpStage = null;

    @Generated
    private SignUpController(){}

    public static SignUpController getInstance(){
        if (signUpController == null)
            signUpController = new SignUpController();
        return signUpController;
    }

    public void handleInitialization() {
        try {
            signUpStage.initializeStage();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("SignUpController");
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }
        signUpStage.show();
    }

    @Generated
    public void setSignUpStage(AbstractSignUpStage signUpStage) {
        this.signUpStage = signUpStage;
    }

    public void handleFieldChanged(String username, String password) {
        int minLength = 4;
        int maxLength = 20;

        boolean usernameValidity = checkStringValidity(username, minLength, maxLength);
        boolean passwordValidity = checkStringValidity(password, minLength, maxLength);

        if (usernameValidity && passwordValidity) {
            if (signUpStage.isSignUpDisabled()) {
                signUpStage.setSignUpButtonAbility(true);
            }
        }
        else if (signUpStage.isSignUpEnabled()) {
            signUpStage.setSignUpButtonAbility(false);
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
