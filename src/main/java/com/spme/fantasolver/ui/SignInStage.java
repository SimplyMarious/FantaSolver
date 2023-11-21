package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.SignInController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SignInStage {

    private final SignInController signInController;
    public TextField username;
    public TextField password;
    public Button signin;
    public Button signup;
    public Label signinFailure;

    public SignInStage() {
        this.signInController = SignInController.getInstance();
        signInController.handleInitialization(this);
    }

    public void initializeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("signin-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        Application.getStage().setTitle("FantaSolver - SignIn");
        Application.getStage().setScene(scene);
        Application.getStage().show();
    }

    public void onPressedSignupButton(MouseEvent mouseEvent) {
        signInController.handlePressedSignupButton();
    }

    public void onPressedSigninButton(MouseEvent mouseEvent) {
        signInController.handlePressedSigninButton(username.toString(), password.toString());
    }

    public void onFieldChanged(InputMethodEvent inputMethodEvent) {
        signInController.handleFieldChanged(username, password);
    }

    public void enableSigninButton() {
        signin.setDisable(false);
    }

    public void disableSigninButton() {
        signin.setDisable(true);
    }

    public boolean isSignInEnable(){
        return !signin.isDisable();
    }

    public boolean isSignInDisable(){
        return signin.isDisable();
    }

    public String getUsername() {return username.toString();}

    public String getPassword() {return password.toString();}

    public void showFailedSignInLabel() {signinFailure.setVisible(true);}
}
