package com.spme.fantasolver.utility;

import com.spme.fantasolver.ui.PopUpStage;

public class ErrorHandler {
    public static void handleInternalError(String message) {
        PopUpStage popUpStage = new PopUpStage(message);
        popUpStage.show();
    }
}
