package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.SignInController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;


public class SignInStage {

    private final SignInController signInController;

    private TextField textFieldUsername;
    private PasswordField fieldPassword;
    private Button buttonSignin;
    private Button buttonSignup;
    private Label labelSigninFailure;

    public SignInStage() {
        this.signInController = SignInController.getInstance();
        signInController.setSignInStage(this);
        signInController.handleInitialization(this);
    }

    public void initializeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("signin-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        textFieldUsername = (TextField) fxmlLoader.getNamespace().get("username");
        fieldPassword = (PasswordField) fxmlLoader.getNamespace().get("password");
        buttonSignup = (Button) fxmlLoader.getNamespace().get("signup");
        buttonSignin = (Button) fxmlLoader.getNamespace().get("signin");
        labelSigninFailure = (Label) fxmlLoader.getNamespace().get("signinFailure");

        buttonSignup.setOnAction(e -> onPressedSignUpButton());
        buttonSignin.setOnAction(e -> onPressedSignInButton());
        textFieldUsername.textProperty().addListener(e -> onFieldChanged() );
        fieldPassword.textProperty().addListener(e -> onFieldChanged() );

        Application.getStage().setTitle("FantaSolver - SignIn");
        Application.getStage().setScene(scene);
        Application.getStage().show();
    }

    public void enableSignInButton() {buttonSignin.setDisable(false);}

    public void disableSignInButton() {buttonSignin.setDisable(true);}

    public boolean isSignInEnable(){return !buttonSignin.isDisable();}

    public boolean isSignInDisable(){return buttonSignin.isDisable();}

    public void showFailedSignInLabel() {labelSigninFailure.setVisible(true);}

    public String getUsername() {return textFieldUsername.getText();}

    private void onPressedSignUpButton() {
        signInController.handlePressedSignUpButton();
    }

    private void onPressedSignInButton() {
        signInController.handlePressedSignInButton(textFieldUsername.getText(), fieldPassword.getText());
    }

    private void onFieldChanged() {
        signInController.handleFieldChanged(textFieldUsername.getText(), fieldPassword.getText());
    }
}


