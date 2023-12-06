package com.spme.fantasolver.dao;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.utility.Utility;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DAOFactory {

    private static TeamDAO teamDAO;
    private static UserDAO userDAO;
    private static FormationDAO formationDAO;

    private static final String CLASS_NAME = "DAOFactory";
    private static final String MYSQL = "MySQL";

    @Generated
    private DAOFactory() {}

    public static TeamDAO getTeamDAO(){
        try {
            tryGetTeamDAO();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(CLASS_NAME);
            logger.info("Error getting the DBMS value for TeamDAO: " + e.getMessage());
            teamDAO = new TeamDAOMySQL();
        }
        return teamDAO;
    }

    @Generated
    private static void tryGetTeamDAO() throws IOException {
        if(teamDAO == null) {
            String teamDAOSource;
            teamDAOSource = Utility.getValueFromProperties("teamDAO");
            teamDAO = createTeamDAO(teamDAOSource);
        }
    }

    public static UserDAO getUserDAO() {
        try {
            tryGetUserDAO();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(CLASS_NAME);
            logger.info("Error getting the DBMS value for UserDAO: " + e.getMessage());
            userDAO = new UserDAOMySQL();
        }
        return userDAO;
    }

    @Generated
    private static void tryGetUserDAO() throws IOException {
        if (userDAO == null) {
            String userDAOSource;
            userDAOSource = Utility.getValueFromProperties("userDAO");
            userDAO = createUserDAO(userDAOSource);
        }
    }

    public static FormationDAO getFormationDAO() {
        try {
            tryGetFormationDAO();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(CLASS_NAME);
            logger.info("Error getting the DBMS value for FormationDAO: " + e.getMessage());
            formationDAO = new FormationDAOMySQL();
        }
        return formationDAO;
    }

    @Generated
    private static void tryGetFormationDAO() throws IOException {
        if (formationDAO == null) {
            String formationDAOSource;
            formationDAOSource = Utility.getValueFromProperties("formationDAO");
            formationDAO = createFormationDAO(formationDAOSource);
        }
    }

    @Generated
    private static TeamDAO createTeamDAO(String source) {
        Map<String, TeamDAO> daoMap = new HashMap<>();
        daoMap.put(MYSQL, new TeamDAOMySQL());

        return daoMap.getOrDefault(source, new TeamDAOMySQL());
    }

    @Generated
    private static UserDAO createUserDAO(String source) {
        Map<String, UserDAO> daoMap = new HashMap<>();
        daoMap.put(MYSQL, new UserDAOMySQL());

        return daoMap.getOrDefault(source, new UserDAOMySQL());
    }

    @Generated
    private static FormationDAO createFormationDAO(String source) {
        Map<String, FormationDAO> daoMap = new HashMap<>();
        daoMap.put(MYSQL, new FormationDAOMySQL());

        return daoMap.getOrDefault(source, new FormationDAOMySQL());
    }

    @Generated
    public static void resetFactory() {
        teamDAO = null;
        userDAO = null;
        formationDAO = null;
    }
}
