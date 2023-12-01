package com.spme.fantasolver.dao;

import com.spme.fantasolver.entity.Formation;

import java.util.Set;

public interface FormationDAO {
    Set<Formation> retrieveFormations();
}
