package com.spme.fantasolver.dao;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class FormationDAOMySQL implements FormationDAO{

    private final Map<String, Formation> formationsMap = new HashMap<>();

    @Override
    public Set<Formation> retrieveFormations() {
        try {
            return tryGetFormations();
        }
        catch (RoleException exception) {
            Logger logger = Logger.getLogger("FormationDAOMySQL");
            logger.info("Error during the retrieve formations: " + exception.getMessage());
            return Collections.emptySet();
        }
    }

    @Generated
    private Set<Formation> tryGetFormations() throws RoleException {
        for (FormationSlot formationSlot : retrieveFormationSlots()) {
            composeFormation(formationSlot);
        }
        return new HashSet<>(formationsMap.values());
    }

    @Generated
    private void composeFormation(FormationSlot formationSlot) throws RoleException {
        String formationName = formationSlot.name;
        short idSlot = formationSlot.idSlot;
        Role role = formationSlot.role;

        if (!isFormationExist(formationName)) {
            Formation formation = new Formation(formationName);
            formationsMap.put(formationName, formation);
        }

        if (!isSlotExist(formationName, idSlot)){
            Slot slot = new Slot(formationSlot.idSlot);
            formationsMap.get(formationName).setSlot(slot);
        }

        formationsMap.get(formationName).addRole(idSlot, role);
    }

    @Generated
    private boolean isFormationExist(String name) {
        for (Formation formation : formationsMap.values()) {
            if (formation.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    @Generated
    private boolean isSlotExist(String formationName, short slotId) {
        if (formationsMap.get(formationName) == null) return false;
        for (Slot slot : formationsMap.get(formationName).getSlots()){
            if (slot.getId() == slotId) {
                return true;
            }
        }
        return false;
    }

    @Generated
    private Set<FormationSlot> retrieveFormationSlots() {
        try {
            return tryGetFormationSlots();
        } catch (ClassNotFoundException | SQLException | RoleNotFoundException exception) {
            Logger logger = Logger.getLogger("FormationDAOMySQL");
            logger.info("Error during the retrieve formations: " + exception.getMessage());
            return Collections.emptySet();
        }
    }

    @Generated
    private Set<FormationSlot> tryGetFormationSlots() throws ClassNotFoundException, SQLException, RoleNotFoundException {
        Set<FormationSlot> formationSlots = new HashSet<>();
        Connection connection = MySQLConnectionManager.connectToDatabase();
        String searchFormations = "SELECT * FROM roled_slot_in_formation";

        try (PreparedStatement preparedStatement = connection.prepareStatement(searchFormations)) {
            ResultSet formationSlot = preparedStatement.executeQuery();
            while(formationSlot.next()) {
                String name = formationSlot.getString(1);
                short idSlot = formationSlot.getShort(2);
                Role role = Role.roleFromString(formationSlot.getString(3));
                formationSlots.add(new FormationSlot(name, idSlot, role));
            }
        }
        connection.close();
        return formationSlots;
    }

    @Generated
    static class FormationSlot {
        private final String name;
        private final short idSlot;
        private final Role role;

        public FormationSlot(String name, short idSlot, Role role){
            this.name = name;
            this.idSlot = idSlot;
            this.role = role;
        }
    }
}
