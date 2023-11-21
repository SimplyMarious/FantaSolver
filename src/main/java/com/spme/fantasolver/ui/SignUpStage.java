package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.SignUpController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class SignUpStage {
    private SignUpController signUpController = null;
    public TextField username;
    public TextField password;
    public Button signup;

    public SignUpStage(){
        this.signUpController = SignUpController.getInstance();
        signUpController.handleInitialization(this);
    }

    public void initializeStage() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("signup-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        Application.getStage().setTitle("FantaSolver - SignUp");
        Application.getStage().setScene(scene);
        Application.getStage().show();
    }

    public void onFieldChanged(InputMethodEvent inputMethodEvent) {
        signUpController.handleFieldChanged(username.toString(), password.toString());
    }

    public void onPressedSignupButton(MouseEvent mouseEvent) {
        signUpController.handlePressedSignupButton(username.toString(), password.toString());
    }

    // TODO: create a window with a success message
    public void showSuccessfulSignup() {

    }

    // TODO: create a window with a failure message
    public void showFailedSignup() {

    }

    public boolean isSignUpDisable() {
        return signup.isDisable();
    }

    public boolean isSignUpEnable() {
        return !signup.isDisable();
    }

    public void enableSignupButton() {
        signup.setDisable(false);
    }

    public void disableSignupButton() {
        signup.setDisable(false);
    }
}
