package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.SignUpController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpStage {
    private final SignUpController signUpController;
    public TextField textFieldUsername;
    public TextField textFieldPassword;
    public Button buttonSignup;

    public SignUpStage(){
        this.signUpController = SignUpController.getInstance();
        signUpController.handleInitialization(this);
    }

    public void initializeStage() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("signup-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        textFieldUsername = (TextField) fxmlLoader.getNamespace().get("username");
        textFieldPassword = (TextField) fxmlLoader.getNamespace().get("password");
        buttonSignup = (Button) fxmlLoader.getNamespace().get("signup");

        buttonSignup.setOnAction(e -> onPressedSignupButton());

        Application.getStage().setTitle("FantaSolver - SignUp");
        Application.getStage().setScene(scene);
        Application.getStage().show();
    }

    public void onFieldChanged() {
        signUpController.handleFieldChanged(textFieldUsername.getText(), textFieldPassword.getText());
    }

    public void onPressedSignupButton() {
        signUpController.handlePressedSignupButton(textFieldUsername.getText(), textFieldPassword.getText());
    }

    // TODO: create a window with a success message
    public void showSuccessfulSignup() {

    }

    // TODO: create a window with a failure message
    public void showFailedSignup() {

    }

    public boolean isSignUpDisable() {
        return buttonSignup.isDisable();
    }

    public boolean isSignUpEnable() {
        return !buttonSignup.isDisable();
    }

    public void enableSignupButton() {
        buttonSignup.setDisable(false);
    }

    public void disableSignupButton() {
        buttonSignup.setDisable(false);
    }
}
