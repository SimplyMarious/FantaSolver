package com.spme.fantasolver.controllers;

import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.ui.ProposeLineupStage;

import java.io.IOException;
import java.util.logging.Logger;

public class ProposeLineupController {
    private static ProposeLineupController proposeLineupController = null;
    private ProposeLineupController(){}

    public static ProposeLineupController getInstance(){
        if(proposeLineupController == null){
            proposeLineupController = new ProposeLineupController();
        }
        return proposeLineupController;
    }


    private ProposeLineupStage proposeLineupStage;

    public void handleInitialization() {
        try {
            proposeLineupStage.initializeStage();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("ProposeLineupController");
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }

        proposeLineupStage.show();

    }

    public void handleSelectedTableViewTeamPlayer() {
        proposeLineupStage.setAddPlayerToLineupButtonAbility(true);
    }

    public void handlePressedAddPlayerToLineupButton(Player player) {
        if(!proposeLineupStage.getLineupPlayers().contains(player)){
            proposeLineupStage.addPlayerToLineupTableView(player);
        }
        else {
            proposeLineupStage.highlightPlayerInTeamTableView(player);
        }
    }

    public void setProposeLineupStage(ProposeLineupStage proposeLineupStage) {
        this.proposeLineupStage = proposeLineupStage;
    }
}
