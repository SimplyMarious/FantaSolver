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

    private Team stubTeam() {
        Set<Player> players = new HashSet<>() {{
            add(new Player("Iezzo", new HashSet<Role>() {{
                add(Role.POR);
            }}));
            add(new Player("Cannavaro", new HashSet<Role>() {{
                add(Role.DC);
            }}));
            add(new Player("Domizzi", new HashSet<Role>() {{
                add(Role.DC);
            }}));
            add(new Player("Maggio", new HashSet<Role>() {{
                add(Role.DC);
                add(Role.C);
            }}));
            add(new Player("Santacroce", new HashSet<Role>() {{
                add(Role.DC);
            }}));
            add(new Player("Gargano", new HashSet<Role>() {{
                add(Role.C);
            }}));
            add(new Player("Hamsik", new HashSet<Role>() {{
                add(Role.C);
            }}));
            add(new Player("Blasi", new HashSet<Role>() {{
                add(Role.C);
            }}));
            add(new Player("Zalayeta", new HashSet<Role>() {{
                add(Role.PC);
            }}));
            add(new Player("Lavezzi", new HashSet<Role>() {{
                add(Role.PC);
            }}));
            add(new Player("Calaio'", new HashSet<Role>() {{
                add(Role.PC);
            }}));
            add(new Player("Navarro", new HashSet<Role>() {{
                add(Role.POR);
            }}));
            add(new Player("Sosa", new HashSet<Role>() {{
                add(Role.C);
            }}));
            add(new Player("Bogliacino", new HashSet<Role>() {{
                add(Role.C);
            }}));
            add(new Player("Mannini", new HashSet<Role>() {{
                add(Role.C);
            }}));
            add(new Player("De Zerbi", new HashSet<Role>() {{
                add(Role.C);
            }}));
            add(new Player("Bogdani", new HashSet<Role>() {{
                add(Role.PC);
            }}));
            add(new Player("Pia", new HashSet<Role>() {{
                add(Role.POR);
            }}));
            add(new Player("Fornaroli", new HashSet<Role>() {{
                add(Role.PC);
            }}));
            add(new Player("Santana", new HashSet<Role>() {{
                add(Role.C);
            }}));
            add(new Player("Garics", new HashSet<Role>() {{
                add(Role.DC);
            }}));
            add(new Player("Cribari", new HashSet<Role>() {{
                add(Role.DC);
            }}));
            add(new Player("Gritti", new HashSet<Role>() {{
                add(Role.POR);
            }}));
        }};

        Team team = new Team("SSC Napoli", players);
        return team;
    }


    static class TeamData{
        private int id;
        private String name;

        public TeamData(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }



}
