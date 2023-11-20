package com.spme.fantasolver.dao;

import com.spme.fantasolver.utility.Utility;

import java.io.IOException;

public class DAOFactory {

    private static TeamDAO teamDAO;

    public static TeamDAO getTeamDAO(){
        if(teamDAO == null){
            String teamDAOSource;
            try {
                teamDAOSource = Utility.getValueFromProperties("teamDAO");
                //TODO: refactor switch
                switch (teamDAOSource){
                    case "MySQL":
                        teamDAO = new TeamDAOMySQL();
                        break;
                    default:
                        teamDAO = new TeamDAOMySQL();
                }
            } catch (IOException e) {
                System.err.println("Error getting the DBMS value for TeamDAO: " + e.getMessage());
                teamDAO = new TeamDAOMySQL();
            }
        }
        return teamDAO;
    }
}
