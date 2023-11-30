package com.spme.fantasolver.dao;

import com.spme.fantasolver.utility.Utility;
import java.io.IOException;
import java.util.logging.Logger;

public class DAOFactory {

    private static TeamDAO teamDAO;
    private static UserDAO userDAO;

    private DAOFactory() {}

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
                Logger logger = Logger.getLogger("DAOFactory");
                logger.info("Error getting the DBMS value for TeamDAO: " + e.getMessage());
                teamDAO = new TeamDAOMySQL();
            }
        }
        return teamDAO;
    }

    public static UserDAO getUserDAO() {
        if (userDAO == null) {
            String userDAOSource;
            try {
                userDAOSource = Utility.getValueFromProperties("userDAO");
                //TODO: refactor switch
                switch (userDAOSource) {
                    case "MySQL":
                        userDAO = new UserDAOMySQL();
                        break;
                    default:
                        userDAO = new UserDAOMySQL();
                }
            } catch (IOException e) {
                Logger logger = Logger.getLogger("DAOFactory");
                logger.info("Error getting the DBMS value for UserDAO: " + e.getMessage());
                userDAO = new UserDAOMySQL();
            }
        }
        return userDAO;
    }

    public static void resetFactory() {
        teamDAO = null;
        userDAO = null;
    }
}
