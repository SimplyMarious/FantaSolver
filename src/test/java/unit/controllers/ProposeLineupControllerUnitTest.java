package unit.controllers;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.ProposeLineupController;
import com.spme.fantasolver.entity.Lineup;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.ui.ProposeLineupStage;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProposeLineupControllerUnitTest {
    private ProposeLineupController proposeLineupController;

    @Mock
    private ProposeLineupStage mockProposeLineupStage;

    @BeforeEach
    public void setUp() {
        proposeLineupController = ProposeLineupController.getInstance();
        mockProposeLineupStage = mock(ProposeLineupStage.class);
        proposeLineupController.setProposeLineupStage(mockProposeLineupStage);
    }

    @Test
    public void testHandleInitializationWithExceptionDuringInitialization() throws IOException {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));

        doThrow(new IOException()).when(mockProposeLineupStage).initializeStage();

        try(MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)){
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger("ProposeLineupController")).thenReturn(mockedLogger);
            doNothing().when(mockedLogger).info(any(String.class));

            assertThrows(FXMLLoadException.class, () -> proposeLineupController.handleInitialization());
        }
    }

    @Test
    public void testHandleSelectedTableViewTeamPlayerWithPlayerSizeLessThanLineupSize() {
        int playersSize = 3;

        proposeLineupController.handleSelectedTableViewTeamPlayer(playersSize);

        verify(mockProposeLineupStage).setAddPlayerToLineupButtonAbility(true);
    }

    @Test
    public void testHandleSelectedTableViewTeamPlayerWithPlayerSizeEqualToLineupSize() {
        int playersSize = 11;

        proposeLineupController.handleSelectedTableViewTeamPlayer(playersSize);

        verify(mockProposeLineupStage, never()).setAddPlayerToLineupButtonAbility(anyBoolean());
    }

    @Test
    public void testHandleSelectedTableViewTeamPlayerWithPlayerSizeGreaterThanLineupSize() {
        int playersSize = 15;

        proposeLineupController.handleSelectedTableViewTeamPlayer(playersSize);

        verify(mockProposeLineupStage, never()).setAddPlayerToLineupButtonAbility(anyBoolean());
    }

    @Test
    public void testHandlePressedAddPlayerToLineupButtonWithEmptyLineup() {
        Player playerToAdd = new Player("TestPlayer1");

        when(mockProposeLineupStage.getLineupPlayers()).thenReturn(new ArrayList<>());

        proposeLineupController.handlePressedAddPlayerToLineupButton(playerToAdd);

        verify(mockProposeLineupStage).addPlayerToLineupTableView(playerToAdd);
        verify(mockProposeLineupStage, never()).highlightPlayerInTeamTableView(playerToAdd);
    }

    @Test
    public void testHandlePressedAddPlayerToLineupButtonWithPlayerNotInLineup() {
        Player playerToAdd = new Player("TestPlayer1");

        when(mockProposeLineupStage.getLineupPlayers()).thenReturn(List.of(
                new Player("TestPlayer2"), new Player("TestPlayer3")));

        proposeLineupController.handlePressedAddPlayerToLineupButton(playerToAdd);

        verify(mockProposeLineupStage).addPlayerToLineupTableView(playerToAdd);
        verify(mockProposeLineupStage, never()).highlightPlayerInTeamTableView(playerToAdd);
    }

    @Test
    public void testHandlePressedAddPlayerToLineupButtonWithPlayerInLineup() {
        Player playerToAdd = new Player("TestPlayer1");

        when(mockProposeLineupStage.getLineupPlayers()).thenReturn(List.of(
                new Player("TestPlayer1"), new Player("TestPlayer2")));

        proposeLineupController.handlePressedAddPlayerToLineupButton(playerToAdd);

        verify(mockProposeLineupStage, never()).addPlayerToLineupTableView(playerToAdd);
        verify(mockProposeLineupStage).highlightPlayerInTeamTableView(playerToAdd);
    }

    @Test
    public void testHandleLineUpTableViewChangedWithLineupSizeEqualsToCorrectLineupSize() {
        int lineupSize = 11;

        proposeLineupController.handleLineUpTableViewChanged(lineupSize);

        verify(mockProposeLineupStage).setAddPlayerToLineupButtonAbility(false);
        verify(mockProposeLineupStage).setVerifyLineupButtonAbility(true);
        verify(mockProposeLineupStage, never()).setRemovePlayerFromLineupButtonAbility(anyBoolean());
    }

    @Test
    public void testHandleLineUpTableViewChangedWithLineupSizeLessThanCorrectLineupSize() {
        int lineupSize = 5;

        proposeLineupController.handleLineUpTableViewChanged(lineupSize);

        verify(mockProposeLineupStage).setAddPlayerToLineupButtonAbility(true);
        verify(mockProposeLineupStage).setVerifyLineupButtonAbility(false);
        verify(mockProposeLineupStage, never()).setRemovePlayerFromLineupButtonAbility(anyBoolean());
    }

    @Test
    public void testHandleLineUpTableViewChangedWithLineupSizeEqualsTo0() {
        int lineupSize = 0;

        proposeLineupController.handleLineUpTableViewChanged(lineupSize);

        verify(mockProposeLineupStage, never()).setAddPlayerToLineupButtonAbility(anyBoolean());
        verify(mockProposeLineupStage, never()).setVerifyLineupButtonAbility(anyBoolean());
        verify(mockProposeLineupStage).setRemovePlayerFromLineupButtonAbility(false);
    }

    @Test
    public void testGetSuitableLineUp(){
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));

        proposeLineupController.initializeFormations();

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

        Lineup lineup = null;
        for (int i = 0 ; i<10; i++) {
            lineup = proposeLineupController.getSuitableLineup(players);
        }

        assertNotNull(lineup);

    }

}
