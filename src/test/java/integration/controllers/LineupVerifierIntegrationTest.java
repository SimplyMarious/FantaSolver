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
import org.mockito.MockedStatic;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class LineupVerifierIntegrationTest {

    @BeforeAll
    static void setUp() {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));

    }

    @Test
    void testGetSuitableLineupWithValidLineup(){
        DAOFactory.resetFactory();

        LineupVerifier lineupVerifier = LineupVerifier.getInstance();
        Set<Player> players = new HashSet<>();
        players.add(new Player("Felipe Anderson", Set.of(Role.W, Role.A)));
        players.add(new Player("Baschirotto", Set.of(Role.DC, Role.DD)));
        players.add(new Player("Anguissa", Set.of(Role.C, Role.M)));
        players.add(new Player("Mkhitaryan", Set.of(Role.C, Role.T)));
        players.add(new Player("Thauvin", Set.of(Role.A)));
        players.add(new Player("Kristiansen", Set.of(Role.DS, Role.E)));
        players.add(new Player("Colpani", Set.of(Role.C, Role.T)));
        players.add(new Player("Lauriente", Set.of(Role.A)));
        players.add(new Player("Provedel", Set.of(Role.POR)));
        players.add(new Player("Sabelli", Set.of(Role.DS, Role.E, Role.DD)));
        players.add(new Player("Acerbi", Set.of(Role.DC)));

        Lineup lineup = lineupVerifier.getSuitableLineup(players);

        assertNotNull(lineup);

    }

    @Test
    void testGetSuitableLineupWithInvalidLineup(){
        DAOFactory.resetFactory();

        LineupVerifier lineupVerifier = LineupVerifier.getInstance();
        Set<Player> players = new HashSet<>();
        players.add(new Player("Felipe Anderson", Set.of(Role.W, Role.A)));
        players.add(new Player("Baschirotto", Set.of(Role.DC, Role.DD)));
        players.add(new Player("Anguissa", Set.of(Role.C, Role.M)));
        players.add(new Player("Mkhitaryan", Set.of(Role.C, Role.T)));
        players.add(new Player("Thauvin", Set.of(Role.A)));
        players.add(new Player("Kristiansen", Set.of(Role.DS, Role.E)));
        players.add(new Player("Colpani", Set.of(Role.C, Role.T)));
        players.add(new Player("Sepe", Set.of(Role.POR)));
        players.add(new Player("Provedel", Set.of(Role.POR)));
        players.add(new Player("Sabelli", Set.of(Role.DS, Role.E, Role.DD)));
        players.add(new Player("Acerbi", Set.of(Role.DC)));

        Lineup lineup = lineupVerifier.getSuitableLineup(players);

        assertNull(lineup);
    }
}
