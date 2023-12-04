package com.spme.fantasolver.ui;

import com.spme.fantasolver.FantaSolver;
import com.spme.fantasolver.annotations.Generated;
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
    private Button buttonSignIn;
    private Button buttonSignUp;
    private Label labelSigninFailure;

    @Generated
    public SignInStage() {
        this.signInController = SignInController.getInstance();
        signInController.setSignInStage(this);
        signInController.handleInitialization();
    }

    @Generated
    public void initializeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FantaSolver.class.getResource("signin-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        textFieldUsername = (TextField) fxmlLoader.getNamespace().get("username");
        fieldPassword = (PasswordField) fxmlLoader.getNamespace().get("password");
        buttonSignUp = (Button) fxmlLoader.getNamespace().get("signup");
        buttonSignIn = (Button) fxmlLoader.getNamespace().get("signin");
        labelSigninFailure = (Label) fxmlLoader.getNamespace().get("signinFailure");

        buttonSignUp.setOnAction(e -> onPressedSignUpButton());
        buttonSignIn.setOnAction(e -> onPressedSignInButton());
        textFieldUsername.textProperty().addListener(e -> onFieldChanged() );
        fieldPassword.textProperty().addListener(e -> onFieldChanged() );

        FantaSolver.getStage().setTitle("FantaSolver - SignIn");
        FantaSolver.getStage().setScene(scene);
        FantaSolver.getStage().show();
    }

    @Generated
    public void setSignInButtonAbility(boolean ability) {
        buttonSignIn.setDisable(!ability);
    }

    @Generated
    public boolean isSignInEnabled(){return !buttonSignIn.isDisable();}

    @Generated
    public boolean isSignInDisabled(){return buttonSignIn.isDisable();}

    @Generated
    public void showFailedSignInLabel() {labelSigninFailure.setVisible(true);}

    @Generated
    public String getUsername() {return textFieldUsername.getText();}

    @Generated
    private void onPressedSignUpButton() {
        signInController.handlePressedSignUpButton();
    }

    @Generated
    private void onPressedSignInButton() {
        signInController.handlePressedSignInButton(textFieldUsername.getText(), fieldPassword.getText());
    }

    @Generated
    private void onFieldChanged() {
        signInController.handleFieldChanged(textFieldUsername.getText(), fieldPassword.getText());
    }
}


