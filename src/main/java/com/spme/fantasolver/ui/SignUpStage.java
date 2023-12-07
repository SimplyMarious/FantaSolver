package com.spme.fantasolver.ui;

import com.spme.fantasolver.FantaSolver;
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

@Generated
public class SignUpStage implements AbstractSignUpStage{
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

    @Override
    public void initializeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FantaSolver.class.getResource("signup-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        textFieldUsername = (TextField) fxmlLoader.getNamespace().get("username");
        fieldPassword = (PasswordField) fxmlLoader.getNamespace().get("password");
        buttonSignUp = (Button) fxmlLoader.getNamespace().get("signup");

        buttonSignUp.setOnAction(e -> onPressedSignUpButton());
        textFieldUsername.textProperty().addListener(e -> onFieldChanged() );
        fieldPassword.textProperty().addListener(e -> onFieldChanged() );

        stage = new Stage();
        stage.setTitle("FantaSolver - SignUp");
        FantaSolver.setIcon(stage);
        stage.setScene(scene);
    }

    @Override
    public void showSuccessfulSignUp() {
        Notifier.notifyInfo("Registrazione completata", "Registrazione completata: sei un Fantallenatore!");
        stage.close();
    }

    @Override
    public void showFailedSignUp() {
        Notifier.notifyError("Registrazione fallita", "Registrazione fallita, riprovare.");
    }

    @Override
    public boolean isSignUpDisabled() {return buttonSignUp.isDisable();}

    @Override
    public boolean isSignUpEnabled() { return !buttonSignUp.isDisable(); }

    @Override
    public void setSignUpButtonAbility(boolean ability) {
        buttonSignUp.setDisable(!ability);
    }

    private void onFieldChanged() {
        signUpController.handleFieldChanged(textFieldUsername.getText(), fieldPassword.getText());
    }

    private void onPressedSignUpButton() {
        signUpController.handlePressedSignUpButton(textFieldUsername.getText(), fieldPassword.getText());
    }

    @Generated
    @Override
    public void show() {
        stage.show();
    }
}
