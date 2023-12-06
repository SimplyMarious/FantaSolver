package unit.controllers;

import com.spme.fantasolver.controllers.LineupVerifier;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.FormationDAO;
import com.spme.fantasolver.entity.Formation;
import com.spme.fantasolver.entity.Lineup;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Slot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.HashSet;
import java.util.List;
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
    void testGetSuitableLineupWithoutFormations(){
        Set<Player> players = new HashSet<>();

        Lineup lineup = lineupVerifier.getSuitableLineup(players);

        assertNull(lineup);
    }

    @Test
    void testGetSuitableLineupWithoutPlayers(){
        try (MockedStatic<Slot> mockSlot = mockStatic(Slot.class)) {
            Set<Player> players = new HashSet<>();
            Lineup lineup;
            Slot[] mockSlots = new Slot[1];
            mockSlots[0] = mock(Slot.class);
            Formation mockFormation = mock(Formation.class);

            when(mockFormation.getSlots()).thenReturn(mockSlots);
            when(mockSlots[0].getId()).thenReturn(0);
            mockSlot.when(()->Slot.sortSlotsByRolesSize(mockSlots)).thenReturn(mockSlots);
            lineupVerifier.setFormations(new HashSet<>(List.of(mockFormation)));

            lineup = lineupVerifier.getSuitableLineup(players);

            assertNull(lineup);
        }
    }

}
