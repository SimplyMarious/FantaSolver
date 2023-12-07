package com.spme.fantasolver.ui;

import com.spme.fantasolver.entity.Player;

import java.util.List;
import java.util.Set;

public interface AbstractManageTeamStage extends AbstractStage {
    void setTextFieldTeamName(String name);
    void setAddPlayerButtonAbility(boolean ability);
    void setRemovePlayerButtonAbility(boolean ability);
    void setConfirmButtonAbility(boolean ability);
    void highlightPlayerInTableView(Player player);
    void loadPlayersInTable(Set<Player> players);
    void addPlayerToTableView(Player player);
    void removePlayerFromTableView(Player player);
    List<Player> getPlayers();
    void close();
}
