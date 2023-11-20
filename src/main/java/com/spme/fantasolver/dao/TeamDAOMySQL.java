package com.spme.fantasolver.dao;

import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.entity.User;

import java.util.HashSet;
import java.util.Set;

public class TeamDAOMySQL implements TeamDAO {
    @Override
    public Team retrieveTeam(User user) {
        return stubTeam();
    }

    private Team stubTeam() {
        Set<Player> players = new HashSet<>() {{
            add(new Player("Iezzo", new HashSet<Role>() {{ add(Role.POR); }}));
            add(new Player("Cannavaro", new HashSet<Role>() {{ add(Role.DC); }}));
            add(new Player("Domizzi", new HashSet<Role>() {{ add(Role.DC); }}));
            add(new Player("Maggio", new HashSet<Role>() {{ add(Role.DC); add(Role.C); }}));
            add(new Player("Santacroce", new HashSet<Role>() {{ add(Role.DC); }}));
            add(new Player("Gargano", new HashSet<Role>() {{ add(Role.C); }}));
            add(new Player("Hamsik", new HashSet<Role>() {{ add(Role.C); }}));
            add(new Player("Blasi", new HashSet<Role>() {{ add(Role.C); }}));
            add(new Player("Zalayeta", new HashSet<Role>() {{ add(Role.PC); }}));
            add(new Player("Lavezzi", new HashSet<Role>() {{ add(Role.PC); }}));
            add(new Player("Calaio'", new HashSet<Role>() {{ add(Role.PC); }}));
            add(new Player("Navarro", new HashSet<Role>() {{ add(Role.POR); }}));
            add(new Player("Sosa", new HashSet<Role>() {{ add(Role.C); }}));
            add(new Player("Bogliacino", new HashSet<Role>() {{ add(Role.C); }}));
            add(new Player("Mannini", new HashSet<Role>() {{ add(Role.C); }}));
            add(new Player("De Zerbi", new HashSet<Role>() {{ add(Role.C); }}));
            add(new Player("Bogdani", new HashSet<Role>() {{ add(Role.PC); }}));
            add(new Player("Pia", new HashSet<Role>() {{ add(Role.POR); }}));
            add(new Player("Fornaroli", new HashSet<Role>() {{ add(Role.PC); }}));
            add(new Player("Santana", new HashSet<Role>() {{ add(Role.C); }}));
            add(new Player("Garics", new HashSet<Role>() {{ add(Role.DC); }}));
            add(new Player("Cribari", new HashSet<Role>() {{ add(Role.DC); }}));
            add(new Player("Gritti", new HashSet<Role>() {{ add(Role.POR); }}));
        }};

        Team team = new Team();
        team.setPlayers(players);
        return team;
    }



}
