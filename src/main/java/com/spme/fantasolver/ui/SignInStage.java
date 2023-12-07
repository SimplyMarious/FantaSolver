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
import javafx.stage.Stage;

import java.io.IOException;


@Generated
public class SignInStage implements AbstractSignInStage {

    private final SignInController signInController;
    private TextField textFieldUsername;
    private PasswordField fieldPassword;
    private Button buttonSignIn;
    private Label labelSigninFailure;
    private Stage stage;


    public SignInStage() {
        this.signInController = SignInController.getInstance();
        signInController.setSignInStage(this);
        signInController.setStageFactory(new JavaFXStageFactory());
        signInController.handleInitialization();
    }

    @Override
    public void initializeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FantaSolver.class.getResource("signin-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        textFieldUsername = (TextField) fxmlLoader.getNamespace().get("username");
        fieldPassword = (PasswordField) fxmlLoader.getNamespace().get("password");
        Button buttonSignUp = (Button) fxmlLoader.getNamespace().get("signup");
        buttonSignIn = (Button) fxmlLoader.getNamespace().get("signin");
        labelSigninFailure = (Label) fxmlLoader.getNamespace().get("signinFailure");

        buttonSignUp.setOnAction(e -> onPressedSignUpButton());
        buttonSignIn.setOnAction(e -> onPressedSignInButton());
        textFieldUsername.textProperty().addListener(e -> onFieldChanged() );
        fieldPassword.textProperty().addListener(e -> onFieldChanged() );

        stage = FantaSolver.getStage();
        stage.setTitle("FantaSolver - SignIn");
        FantaSolver.setIcon(FantaSolver.getStage());
        FantaSolver.getStage().setScene(scene);
    }

    @Override
    public void setSignInButtonAbility(boolean ability) {
        buttonSignIn.setDisable(!ability);
    }

    @Override
    public boolean isSignInEnabled(){return !buttonSignIn.isDisable();}

    @Override
    public boolean isSignInDisabled(){return buttonSignIn.isDisable();}

    @Override
    public void showFailedSignInLabel() {labelSigninFailure.setVisible(true);}

    @Override
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

    @Generated
    @Override
    public void show() {
        stage.show();
    }
}


