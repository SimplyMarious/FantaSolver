package com.spme.fantasolver.ui;

import com.spme.fantasolver.entity.Player;

import java.util.List;

public interface AbstractProposeLineupStage extends AbstractStage{
    void setAddPlayerToLineupButtonAbility(boolean ability);
    void setRemovePlayerFromLineupButtonAbility(boolean ability);
    void setVerifyLineupButtonAbility(boolean ability);
    void highlightPlayerInTeamTableView(Player player);
    void addPlayerToLineupTableView(Player player);
    void removePlayerFromLineupTableView(Player player);
    List<Player> getLineupPlayers();
}
