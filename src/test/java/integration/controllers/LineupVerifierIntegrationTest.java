package integration.controllers;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.controllers.LineupVerifier;
import com.spme.fantasolver.dao.DAOFactory;

import com.spme.fantasolver.entity.Lineup;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LineupVerifierIntegrationTest {

    @BeforeAll
    public static void setUp() {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));

    }

    @Test
    public void testGetSuitableLineupWithPlayers(){
        DAOFactory.resetFactory();
        LineupVerifier lineupVerifier = LineupVerifier.getInstance();

        Set<Player> players = new HashSet<>();

        players.add(new Player("Felipe Anderson", new HashSet<>(List.of(new Role[]{Role.A, Role.W}))));
        players.add(new Player("Baschirotto", new HashSet<>(List.of(new Role[]{Role.DC, Role.DD}))));
        players.add(new Player("Anguissa", new HashSet<>(List.of(new Role[]{Role.C, Role.M}))));
        players.add(new Player("Mkhit", new HashSet<>(List.of(new Role[]{Role.C, Role.T}))));
        players.add(new Player("Thauvin", new HashSet<>(List.of(new Role[]{Role.A}))));
        players.add(new Player("Kristiansen", new HashSet<>(List.of(new Role[]{Role.DS, Role.E}))));
        players.add(new Player("Colpani", new HashSet<>(List.of(new Role[]{Role.C, Role.T}))));
        players.add(new Player("Lauriente", new HashSet<>(List.of(new Role[]{Role.A}))));
        players.add(new Player("Provedel", new HashSet<>(List.of(new Role[]{Role.POR}))));
        players.add(new Player("Sabelli", new HashSet<>(List.of(new Role[]{Role.DS, Role.E, Role.DD}))));
        players.add(new Player("Acerbi", new HashSet<>(List.of(new Role[]{Role.DC}))));

        Lineup lineup = lineupVerifier.getSuitableLineup(players);

        assertNotNull(lineup);
    }
}
