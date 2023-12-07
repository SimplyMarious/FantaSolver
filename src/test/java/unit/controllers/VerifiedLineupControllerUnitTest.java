package unit.controllers;

import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.VerifiedLineupController;
import com.spme.fantasolver.entity.*;
import com.spme.fantasolver.ui.VerifiedLineupStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VerifiedLineupControllerUnitTest {

    private VerifiedLineupController verifiedLineupController;

    @Mock
    private VerifiedLineupStage mockVerifiedLineupStage;

    @Mock
    private Lineup mockLineup;

    @BeforeEach
    void setUp() {
        mockVerifiedLineupStage = mock(VerifiedLineupStage.class);
        verifiedLineupController = VerifiedLineupController.getInstance();
        verifiedLineupController.setVerifiedLineupStage(mockVerifiedLineupStage);
        mockLineup = mock(Lineup.class);
    }

    @Test
    void testGetInstance() {
        assertNotNull(verifiedLineupController);
        assertSame(verifiedLineupController, VerifiedLineupController.getInstance());
    }

    @Test
    void testHandleInitializationSuccess() throws IOException {
        Lineup mockLineup = mock(Lineup.class);
        Formation mockFormation = mock(Formation.class);
        Player[] mockPlayers = new Player[1];
        Slot[] mockSlots = new Slot[1];
        mockPlayers[0] = mock(Player.class);
        mockSlots[0] = mock(Slot.class);

        doNothing().when(mockVerifiedLineupStage).initializeStage();
        doNothing().when(mockVerifiedLineupStage).setLineupFormationLabelText(anyString());
        doNothing().when(mockVerifiedLineupStage).loadPlayersInTable(mockPlayers);
        doNothing().when(mockVerifiedLineupStage).show();
        when(mockLineup.getFormation()).thenReturn(mockFormation);
        when(mockFormation.getSlots()).thenReturn(mockSlots);
        when(mockLineup.getPlayers()).thenReturn(mockPlayers);

        verifiedLineupController.handleInitialization(mockLineup);

        verify(mockVerifiedLineupStage, times(1)).initializeStage();
        verify(mockVerifiedLineupStage, times(1)).setLineupFormationLabelText("Il tuo modulo: " + mockLineup.getFormation().getName());
        verify(mockVerifiedLineupStage, times(1)).loadPlayersInTable(mockPlayers);
        verify(mockVerifiedLineupStage, times(1)).show();
    }
    @Test
    void testHandleInitializationWithFailure() throws IOException {
        doThrow(new IOException()).when(mockVerifiedLineupStage).initializeStage();

        try (MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)) {
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger(eq("VerifiedLineupController"))).thenReturn(mockedLogger);
            doNothing().when(mockedLogger).info(any(String.class));

            assertThrows(FXMLLoadException.class, () -> verifiedLineupController.handleInitialization(mockLineup));
        }
    }
}
