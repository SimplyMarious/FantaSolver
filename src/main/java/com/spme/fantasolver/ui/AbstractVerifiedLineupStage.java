package com.spme.fantasolver.ui;

import com.spme.fantasolver.entity.Player;

public interface AbstractVerifiedLineupStage extends AbstractStage{
    void setLineupFormationLabelText(String text);
    void loadPlayersInTable(Player[] players);
}
