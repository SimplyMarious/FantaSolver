package com.spme.fantasolver.dao;

import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.entity.User;

public interface TeamDAO {
    Team retrieveTeam(User user) throws InternalException;

    boolean updateTeam(Team team, User user);
}
