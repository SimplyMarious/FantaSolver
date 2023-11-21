package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.SignInController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

import java.io.IOException;


public class SignInStage {

    private final SignInController signInController;

    public TextField textFieldUsername;
    public TextField textFieldPassword;
    public Button buttonSignin;
    public Button buttonSignup;
    public Label labelSigninFailure;

    public SignInStage() {
        this.signInController = SignInController.getInstance();
        signInController.handleInitialization(this);
    }

    public void initializeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("signin-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        textFieldUsername = (TextField) fxmlLoader.getNamespace().get("username");
        textFieldPassword = (TextField) fxmlLoader.getNamespace().get("password");
        buttonSignup = (Button) fxmlLoader.getNamespace().get("signup");
        buttonSignin = (Button) fxmlLoader.getNamespace().get("signin");
        labelSigninFailure = (Label) fxmlLoader.getNamespace().get("signinFailure");

        buttonSignup.setOnAction(e -> onPressedSignupButton());
        buttonSignin.setOnAction(e -> onPressedSigninButton());

        Application.getStage().setTitle("FantaSolver - SignIn");
        Application.getStage().setScene(scene);
        Application.getStage().show();
    }

    public void onPressedSignupButton() {
        labelSigninFailure.setVisible(true);
        signInController.handlePressedSignupButton();
    }

    public void onPressedSigninButton() {
        signInController.handlePressedSigninButton(textFieldUsername.getText(), textFieldPassword.getText());
    }

    public void onFieldChanged(InputMethodEvent inputMethodEvent) {
        signInController.handleFieldChanged(textFieldUsername.getText(), textFieldPassword.getText());
    }

    public void enableSigninButton() {buttonSignin.setDisable(false);}

    public void disableSigninButton() {buttonSignin.setDisable(true);}

    public boolean isSignInEnable(){return !buttonSignin.isDisable();}

    public boolean isSignInDisable(){return buttonSignin.isDisable();}

    public void showFailedSignInLabel() {labelSigninFailure.setVisible(true);}

}


