package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.controllers.SignUpController;
import com.spme.fantasolver.utility.Notifier;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpStage {
    private final SignUpController signUpController;
    private TextField textFieldUsername;
    private PasswordField fieldPassword;
    private Button buttonSignUp;
    private Stage stage;

    public SignUpStage(){
        this.signUpController = SignUpController.getInstance();
        signUpController.setSignUpStage(this);
        signUpController.handleInitialization();
    }

    @Generated
    public void initializeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("signup-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        textFieldUsername = (TextField) fxmlLoader.getNamespace().get("username");
        fieldPassword = (PasswordField) fxmlLoader.getNamespace().get("password");
        buttonSignUp = (Button) fxmlLoader.getNamespace().get("signup");

        buttonSignUp.setOnAction(e -> onPressedSignUpButton());
        textFieldUsername.textProperty().addListener(e -> onFieldChanged() );
        fieldPassword.textProperty().addListener(e -> onFieldChanged() );

        stage = new Stage();
        stage.setTitle("FantaSolver - SignUp");
        stage.setScene(scene);
        stage.show();
    }

    @Generated
    public void showSuccessfulSignUp() {
        Notifier.notifyInfo("Registrazione completata", "Registrazione completata: sei un Fantallenatore!");
        stage.close();
    }

    @Generated
    public void showFailedSignUp() {
        Notifier.notifyError("Registrazione fallita", "Registrazione fallita, riprovare.");
    }

    @Generated
    public boolean isSignUpDisabled() {return buttonSignUp.isDisable();}

    @Generated
    public boolean isSignUpEnabled() { return !buttonSignUp.isDisable(); }

    @Generated
    public void setSignUpButtonAbility(boolean ability) {
        buttonSignUp.setDisable(!ability);
    }

    @Generated
    private void onFieldChanged() {
        signUpController.handleFieldChanged(textFieldUsername.getText(), fieldPassword.getText());
    }

    @Generated
    private void onPressedSignUpButton() {
        signUpController.handlePressedSignUpButton(textFieldUsername.getText(), fieldPassword.getText());
    }
}
