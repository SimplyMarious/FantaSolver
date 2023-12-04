package com.spme.fantasolver.dao;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.utility.Utility;
import java.io.IOException;
import java.util.logging.Logger;

public class DAOFactory {

    private static TeamDAO teamDAO;
    private static UserDAO userDAO;
    private static FormationDAO formationDAO;

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

    public static FormationDAO getFormationDAO() {
        if (formationDAO == null) {
            String formationDAOSource;
            try {
                formationDAOSource = Utility.getValueFromProperties("formationDAO");
                //TODO: refactor switch
                switch (formationDAOSource) {
                    case "MySQL":
                        formationDAO = new FormationDAOMySQL();
                        break;
                    default:
                        formationDAO = new FormationDAOMySQL();
                }
            } catch (IOException e) {
                Logger logger = Logger.getLogger("DAOFactory");
                logger.info("Error getting the DBMS value for FormationDAO: " + e.getMessage());
                formationDAO = new FormationDAOMySQL();            }

        }
        return formationDAO;
    }

    @Generated
    public static void resetFactory() {
        teamDAO = null;
        userDAO = null;
        formationDAO = null;
    }
}
