package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.SignUpController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpStage {
    private final SignUpController signUpController;
    public TextField textFieldUsername;
    public PasswordField fieldPassword;
    public Button buttonSignup;

    public SignUpStage(){
        this.signUpController = SignUpController.getInstance();
        signUpController.handleInitialization(this);
    }

    public void initializeStage() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("signup-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        textFieldUsername = (TextField) fxmlLoader.getNamespace().get("username");
        fieldPassword = (PasswordField) fxmlLoader.getNamespace().get("password");
        buttonSignup = (Button) fxmlLoader.getNamespace().get("signup");

        buttonSignup.setOnAction(e -> onPressedSignUpButton());
        textFieldUsername.textProperty().addListener(e -> onFieldChanged() );
        fieldPassword.textProperty().addListener(e -> onFieldChanged() );

        Stage signUpStage = new Stage();
        signUpStage.setTitle("FantaSolver - SignUp");
        signUpStage.setScene(scene);
        signUpStage.show();
    }

    public void onFieldChanged() {
        signUpController.handleFieldChanged(textFieldUsername.getText(), fieldPassword.getText());
    }

    public void onPressedSignUpButton() {
        signUpController.handlePressedSignUpButton(textFieldUsername.getText(), fieldPassword.getText());
    }

    public void showSuccessfulSignUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrazione completata.");
        alert.setHeaderText(null);
        alert.setContentText("Operazione completata con successo!");

        alert.showAndWait();
    }

    public void showFailedSignUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrazione fallita.");
        alert.setHeaderText(null);
        alert.setContentText("Operazione fallita.");

        alert.showAndWait();
    }

    public boolean isSignUpDisable() {return buttonSignup.isDisable();}

    public boolean isSignUpEnable() { return !buttonSignup.isDisable(); }

    public void enableSignUpButton() {buttonSignup.setDisable(false);}

    public void disableSignUpButton() { buttonSignup.setDisable(true); }
}
