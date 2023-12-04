package unit.controllers;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.controllers.LineupVerifier;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.FormationDAO;
import com.spme.fantasolver.entity.Formation;
import com.spme.fantasolver.entity.Lineup;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class LineupVerifierUnitTest {

    private static LineupVerifier lineupVerifier;

    @Mock
    private MockedStatic<DAOFactory> mockDAOFactory;

    @Mock
    private FormationDAO mockFormationDAO;

    @BeforeEach
    public void setUp() {
        mockDAOFactory = mockStatic(DAOFactory.class);
        mockFormationDAO = mock(FormationDAO.class);
        mockDAOFactory.when(DAOFactory::getFormationDAO).thenReturn(mockFormationDAO);
        when(mockFormationDAO.retrieveFormations()).thenReturn(new HashSet<>());

        lineupVerifier = LineupVerifier.getInstance();
    }

    @AfterEach
    public void clean() {
        mockDAOFactory.close();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(lineupVerifier);
        assertSame(lineupVerifier, LineupVerifier.getInstance());
    }

    @Test
    public void testGetSuitableLineupWithoutPlayers(){
        Set<Player> players = new HashSet<>();

        Lineup lineup = lineupVerifier.getSuitableLineup(players);

        assertNull(lineup);
    }

}
