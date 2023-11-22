package com.spme.fantasolver;

import com.spme.fantasolver.controllers.SignInController;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private static Stage stage;
    @Override
    public void start(Stage stage){
        setStage(stage);

        SignInController signInController = SignInController.getInstance();
        signInController.handlePressedSignInButton("TestUsername", "TestPassword");
    }

    public static Stage getStage(){
        return stage;
    }

    private static void setStage(Stage stage) {
        Application.stage = stage;
    }

    public static void main(String[] args) {
        launch();
    }

}

