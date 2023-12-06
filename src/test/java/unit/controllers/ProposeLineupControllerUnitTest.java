package unit.controllers;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.LineupVerifier;
import com.spme.fantasolver.controllers.ProposeLineupController;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.ui.ProposeLineupStage;
import com.spme.fantasolver.utility.Notifier;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProposeLineupControllerUnitTest {
    private ProposeLineupController proposeLineupController;

    @Mock
    private ProposeLineupStage mockProposeLineupStage;

    @BeforeEach
    void setUp() {
        proposeLineupController = ProposeLineupController.getInstance();
        mockProposeLineupStage = mock(ProposeLineupStage.class);
        proposeLineupController.setProposeLineupStage(mockProposeLineupStage);
    }

    @Test
    void testHandleInitializationSuccess() throws IOException {
        doNothing().when(mockProposeLineupStage).initializeStage();
        doNothing().when(mockProposeLineupStage).show();

        proposeLineupController.handleInitialization();

        verify(mockProposeLineupStage, times(1)).show();
    }

    @Test
    void testHandlePressedVerifyLineupButton(){
        MockedStatic<LineupVerifier> mockStaticLineupVerifier = mockStatic(LineupVerifier.class);
        LineupVerifier mockLineupVerifier = mock(LineupVerifier.class);
        MockedStatic<Notifier> mockNotifier = mockStatic(Notifier.class);
        mockStaticLineupVerifier.when(LineupVerifier::getInstance).thenReturn(mockLineupVerifier);

        when(mockLineupVerifier.getSuitableLineup(any(Set.class))).thenReturn(null);

        proposeLineupController.handlePressedVerifyLineupButton(new HashSet<>());

        mockNotifier.verify(() ->
                Notifier.notifyInfo(anyString(), anyString()), times(1));

        mockStaticLineupVerifier.close();
        mockNotifier.close();
    }

    @Test
    void testHandleInitializationWithExceptionDuringInitialization() throws IOException {
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
    void testHandleSelectedTableViewTeamPlayerWithPlayerSizeLessThanLineupSize() {
        int playersSize = 3;

        proposeLineupController.handleSelectedTableViewTeamPlayer(playersSize);

        verify(mockProposeLineupStage).setAddPlayerToLineupButtonAbility(true);
    }

    @Test
    void testHandleSelectedTableViewTeamPlayerWithPlayerSizeEqualToLineupSize() {
        int playersSize = 11;

        proposeLineupController.handleSelectedTableViewTeamPlayer(playersSize);

        verify(mockProposeLineupStage, never()).setAddPlayerToLineupButtonAbility(anyBoolean());
    }

    @Test
    void testHandleSelectedTableViewTeamPlayerWithPlayerSizeGreaterThanLineupSize() {
        int playersSize = 15;

        proposeLineupController.handleSelectedTableViewTeamPlayer(playersSize);

        verify(mockProposeLineupStage, never()).setAddPlayerToLineupButtonAbility(anyBoolean());
    }

    @Test
    void testHandlePressedAddPlayerToLineupButtonWithEmptyLineup() {
        Player playerToAdd = new Player("TestPlayer1");

        when(mockProposeLineupStage.getLineupPlayers()).thenReturn(new ArrayList<>());

        proposeLineupController.handlePressedAddPlayerToLineupButton(playerToAdd);

        verify(mockProposeLineupStage).addPlayerToLineupTableView(playerToAdd);
        verify(mockProposeLineupStage, never()).highlightPlayerInTeamTableView(playerToAdd);
    }

    @Test
    void testHandlePressedAddPlayerToLineupButtonWithPlayerNotInLineup() {
        Player playerToAdd = new Player("TestPlayer1");

        when(mockProposeLineupStage.getLineupPlayers()).thenReturn(List.of(
                new Player("TestPlayer2"), new Player("TestPlayer3")));

        proposeLineupController.handlePressedAddPlayerToLineupButton(playerToAdd);

        verify(mockProposeLineupStage).addPlayerToLineupTableView(playerToAdd);
        verify(mockProposeLineupStage, never()).highlightPlayerInTeamTableView(playerToAdd);
    }

    @Test
    void testHandlePressedAddPlayerToLineupButtonWithPlayerInLineup() {
        Player playerToAdd = new Player("TestPlayer1");

        when(mockProposeLineupStage.getLineupPlayers()).thenReturn(List.of(
                new Player("TestPlayer1"), new Player("TestPlayer2")));

        proposeLineupController.handlePressedAddPlayerToLineupButton(playerToAdd);

        verify(mockProposeLineupStage, never()).addPlayerToLineupTableView(playerToAdd);
        verify(mockProposeLineupStage).highlightPlayerInTeamTableView(playerToAdd);
    }

    @Test
    void testHandleLineUpTableViewChangedWithLineupSizeEqualsToCorrectLineupSize() {
        int lineupSize = 11;

        proposeLineupController.handleLineUpTableViewChanged(lineupSize);

        verify(mockProposeLineupStage).setAddPlayerToLineupButtonAbility(false);
        verify(mockProposeLineupStage).setVerifyLineupButtonAbility(true);
        verify(mockProposeLineupStage, never()).setRemovePlayerFromLineupButtonAbility(anyBoolean());
    }

    @Test
    void testHandleLineUpTableViewChangedWithLineupSizeLessThanCorrectLineupSize() {
        int lineupSize = 5;

        proposeLineupController.handleLineUpTableViewChanged(lineupSize);

        verify(mockProposeLineupStage).setAddPlayerToLineupButtonAbility(true);
        verify(mockProposeLineupStage).setVerifyLineupButtonAbility(false);
        verify(mockProposeLineupStage, never()).setRemovePlayerFromLineupButtonAbility(anyBoolean());
    }

    @Test
    void testHandleLineUpTableViewChangedWithLineupSizeEqualsTo0() {
        int lineupSize = 0;

        proposeLineupController.handleLineUpTableViewChanged(lineupSize);

        verify(mockProposeLineupStage, never()).setAddPlayerToLineupButtonAbility(anyBoolean());
        verify(mockProposeLineupStage, never()).setVerifyLineupButtonAbility(anyBoolean());
        verify(mockProposeLineupStage).setRemovePlayerFromLineupButtonAbility(false);
    }
}
