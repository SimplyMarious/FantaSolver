package com.spme.fantasolver;

import com.spme.fantasolver.ui.SignInStage;
import javafx.application.Platform;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        setStage(stage);
        new SignInStage();

        stage.setOnCloseRequest(e -> {
            e.consume();
            closeApplication();
        });
    }

    public static Stage getStage(){
        return stage;
    }

    private static void setStage(Stage stage) {
        Application.stage = stage;
    }

    private void closeApplication() {
        Platform.exit();
    }
    public static void main(String[] args) {
        launch();
    }

}

