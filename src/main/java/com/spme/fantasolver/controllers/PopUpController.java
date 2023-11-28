package com.spme.fantasolver.controllers;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.ui.PopUpStage;
import javafx.application.Platform;

import java.io.IOException;
import java.util.logging.Logger;

import com.spme.fantasolver.Application;

public class PopUpController {
    private static PopUpController popUpController = null;
    private PopUpController(){}

    public static PopUpController getInstance(){
        if(popUpController == null){
            popUpController = new PopUpController();
        }
        return popUpController;
    }


    private PopUpStage popUpStage;

    public void setPopUpStage(PopUpStage popUpStage){
        this.popUpStage = popUpStage;
    }

    public void handleInitialization(){
        try {
            popUpStage.initializeStage();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("PopUpController");
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }
    }

    public void handlePressedOKButton() {
        Application.closeApplication();
    }
}
