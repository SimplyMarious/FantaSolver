package com.spme.fantasolver;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.ui.JavaFXStageFactory;
import com.spme.fantasolver.ui.SignInStage;
import com.spme.fantasolver.ui.StageFactory;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class FantaSolver extends javafx.application.Application {
    private static Stage stage;

    @Generated
    @Override
    public void start(Stage stage){
        setStage(stage);
        StageFactory factory = new JavaFXStageFactory();
        factory.createSignInStage();

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
    public static void setIcon(Stage stage) {
        stage.getIcons().add(new Image(Objects.requireNonNull(
                SignInStage.class.getResourceAsStream("/com/spme/fantasolver/fantasolver_logo.png"))));
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

