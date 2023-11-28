package com.spme.fantasolver.dao;

import com.spme.fantasolver.controllers.ManageTeamController;
import com.spme.fantasolver.entity.*;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class TeamDAOMySQL implements TeamDAO {
    private Connection connection;
    @Override
    public Team retrieveTeam(User user) throws InternalException {
        try {
            connection = MySQLConnectionManager.connectToDatabase();
            TeamData teamData = retrieveTeamData(user);
            if (teamData != null) {
                Map<String, Set<String>> retrievedPlayersWithRoles = retrieveTeamPlayers(teamData.id);
                Set<Player> teamPlayers = getTeamPlayers(retrievedPlayersWithRoles);
                return new Team(teamData.name, teamPlayers);
            }
            else {
                return null;
            }
        } catch (ClassNotFoundException | SQLException | RoleException exception) {
            Logger logger = Logger.getLogger("TeamDAOMySQL");
            logger.info("Error during the team retrieve: " + exception.getMessage());
            throw new InternalException();
        }
    }

    private TeamData retrieveTeamData(User user) throws SQLException {
        String query = "SELECT id, name " +
                "FROM team " +
                "WHERE user_name = ?";
        System.out.println(query);
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                return null;
            }
            else{
                return new TeamData(resultSet.getInt(1), resultSet.getString(2));
            }
        }

    }

    private Map<String, Set<String>> retrieveTeamPlayers(int teamID) throws SQLException {
        String query =
                "SELECT player_name, role_name " +
                        "FROM role_in_player " +
                        "WHERE player_name IN (SELECT player_name " +
                        "FROM player_in_team " +
                        "WHERE team_id = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, teamID);
        ResultSet resultSet = preparedStatement.executeQuery();

        Map<String, Set<String>> retrievedPlayersWithRoles = new HashMap<>();
        while(resultSet.next()){
            String playerName = resultSet.getString(1);
            String playerRole = resultSet.getString(2);

            if(!retrievedPlayersWithRoles.containsKey(playerName)){
                retrievedPlayersWithRoles.put(playerName, new HashSet<>());
            }
            retrievedPlayersWithRoles.get(playerName).add(playerRole);
        }
        return retrievedPlayersWithRoles;
    }

    private static Set<Player> getTeamPlayers(Map<String, Set<String>> retrievedPlayersWithRoles) throws RoleException {
        Set<Player> teamPlayers = new HashSet<>();
        for(String playerName: retrievedPlayersWithRoles.keySet()) {
            Player player = new Player(playerName);
            for (String playerRole: retrievedPlayersWithRoles.get(playerName)) {
                player.addRole(Role.valueOf(playerRole));
            }
            teamPlayers.add(player);
        }
        return teamPlayers;
    }

    @Override
    public boolean updateTeam(Team team, User user) {
        try{
            Connection connection = MySQLConnectionManager.connectToDatabase();
        }
        catch (ClassNotFoundException | SQLException exception){
            Logger logger = Logger.getLogger("TeamDAOMySQL");
            logger.info("Error during the team retrieve: " + exception.getMessage());
            return false;
        }

        //TODO: insert players -> insert players' roles
        // select team id from user -> (update team name || insert team -> update user's team)
        // select team id from user -> insert player in team
        return true;
    }

    static class TeamData{
        private final int id;
        private final String name;

        public TeamData(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }



}
