package com.spme.fantasolver;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.ui.SignInStage;
import javafx.application.Platform;
import javafx.stage.Stage;

public class FantaSolver extends javafx.application.Application {
    private static Stage stage;

    @Generated
    @Override
    public void start(Stage stage){
        setStage(stage);
        new SignInStage();

        stage.setOnCloseRequest(e -> {
            e.consume();
            closeApplication();
        });
    }

    @Generated
    public static Stage getStage(){
        return stage;
    }

    @Generated
    private static void setStage(Stage stage) {
        FantaSolver.stage = stage;
    }

    @Generated
    public static void closeApplication() {
        Platform.exit();
    }

    @Generated
    public static void main(String[] args) {
        launch();
    }

}

