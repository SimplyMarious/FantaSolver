package com.spme.fantasolver.dao;

import com.spme.fantasolver.annotations.Generated;
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

    @Generated
    private TeamData retrieveTeamData(User user) throws SQLException {
        String query = "SELECT id, name " +
                "FROM team " +
                "WHERE user_name = ?";
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

    @Generated
    private Map<String, Set<String>> retrieveTeamPlayers(int teamID) throws SQLException {
        String query =  "SELECT player_name, role_name " +
                        "FROM role_in_player " +
                        "WHERE player_name IN (SELECT player_name " +
                        "FROM player_in_team " +
                        "WHERE team_id = ?)";
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teamID);
            resultSet = preparedStatement.executeQuery();
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
    }

    @Generated
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
            connection = MySQLConnectionManager.connectToDatabase();
            if(deleteCurrentUserTeam(user) > 1){ return false; }
            if(insertNewTeam(team, user) != 1){ return false; }
            if(insertPlayersInTeam(team) != team.getPlayers().size()) { return false; }

            return true;
        }
        catch (ClassNotFoundException | SQLException exception){
            Logger logger = Logger.getLogger("TeamDAOMySQL");
            logger.info("Error during the team update: " + exception.getMessage());
            return false;
        }
    }

    @Generated
    private int deleteCurrentUserTeam(User user) throws SQLException {
        String query = "DELETE FROM team " +
                "WHERE user_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Deletion: " + affectedRows);
            return affectedRows;
        }
    }

    @Generated
    private int insertNewTeam(Team team, User user) throws SQLException {
        String query = "INSERT IGNORE INTO team(name, user_name) " +
                "VALUES (?, ?); " +
                "UPDATE user " +
                "SET team_id = (select id from team where user_name = ?) " +
                "WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getUsername());
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("New team insertion: " + affectedRows);
            return affectedRows;
        }
    }

    @Generated
    private int insertPlayersInTeam(Team team) throws SQLException {
        int playersCorrectlyInserted = 0;
        for(Player player: team.getPlayers()){
            insertPlayer(player);
            insertPlayerRoles(player);
            playersCorrectlyInserted += linkPlayerToTeam(player, team);
            System.out.println("Player correctly inserted: " + playersCorrectlyInserted);
        }
        return playersCorrectlyInserted;
    }

    @Generated
    private void insertPlayer(Player player) throws SQLException {
        String query = "INSERT IGNORE INTO player " +
                       "VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.getName());
            preparedStatement.execute();
        }
    }

    @Generated
    private void insertPlayerRoles(Player player) throws SQLException {
        String query = "INSERT IGNORE INTO role_in_player " +
                       "VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for(Role role: player.getRoles()){
                preparedStatement.setString(1, player.getName());
                preparedStatement.setString(2, role.toString());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    @Generated
    private int linkPlayerToTeam(Player player, Team team) throws SQLException {
        String query = "INSERT INTO player_in_team " +
                       "SELECT DISTINCT player.name, team.id " +
                       "FROM player, team " +
                       "WHERE player.name = ? AND team.name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.getName());
            preparedStatement.setString(2, team.getName());
            return preparedStatement.executeUpdate();
        }
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
