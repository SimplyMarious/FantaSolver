package unit.controllers;

import com.spme.fantasolver.controllers.LineupVerifier;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.FormationDAO;
import com.spme.fantasolver.entity.Lineup;
import com.spme.fantasolver.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LineupVerifierUnitTest {

    private static LineupVerifier lineupVerifier;

    @Mock
    private MockedStatic<DAOFactory> mockDAOFactory;

    @Mock
    private FormationDAO mockFormationDAO;

    @BeforeEach
    void setUp() {
        mockDAOFactory = mockStatic(DAOFactory.class);
        mockFormationDAO = mock(FormationDAO.class);
        mockDAOFactory.when(DAOFactory::getFormationDAO).thenReturn(mockFormationDAO);
        when(mockFormationDAO.retrieveFormations()).thenReturn(new HashSet<>());

        lineupVerifier = LineupVerifier.getInstance();
    }

    @AfterEach
    void clean() {
        mockDAOFactory.close();
    }

    @Test
    void testGetInstance() {
        assertNotNull(lineupVerifier);
        assertSame(lineupVerifier, LineupVerifier.getInstance());
    }

    @Test
    void testGetSuitableLineupWithoutPlayers(){
        Set<Player> players = new HashSet<>();

        Lineup lineup = lineupVerifier.getSuitableLineup(players);

        assertNull(lineup);
    }

}
