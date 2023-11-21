package com.spme.fantasolver.controllers;
import com.spme.fantasolver.ui.SignUpStage;
import java.io.IOException;
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
            System.err.println("Error in reading FXML file.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void handleFieldChanged(String username, String password) {
        int minLength = 4;
        int maxLength = 20;

        boolean usernameValidity = checkStringValidity(username, minLength, maxLength);
        boolean passwordValidity = checkStringValidity(password, minLength, maxLength);

        if (usernameValidity && passwordValidity)
            if (signUpStage.isSignUpDisable())
                signUpStage.enableSignupButton();
            else
            if (signUpStage.isSignUpEnable())
                signUpStage.disableSignupButton();
    }

    // TODO: try to create a new account
    public void handlePressedSignupButton(String username, String password) {
    }
}
