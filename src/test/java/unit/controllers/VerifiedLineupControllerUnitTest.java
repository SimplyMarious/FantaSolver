package unit.controllers;

import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.VerifiedLineupController;
import com.spme.fantasolver.entity.Lineup;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.ui.VerifiedLineupStage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VerifiedLineupControllerUnitTest {

    private VerifiedLineupController verifiedLineupController;
    @Mock
    private VerifiedLineupStage mockVerifiedLineupStage;

    @BeforeEach
    void setUp() {
        mockVerifiedLineupStage = mock(VerifiedLineupStage.class);
        verifiedLineupController = VerifiedLineupController.getInstance();
        verifiedLineupController.setVerifiedLineupStage(mockVerifiedLineupStage);
    }


    @Test
    void testHandleInitializationWithFailure() throws IOException {
        doThrow(new IOException()).when(mockVerifiedLineupStage).initializeStage();

        try (MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)) {
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger(eq("VerifiedLineupController"))).thenReturn(mockedLogger);
            doNothing().when(mockedLogger).info(any(String.class));

            assertThrows(FXMLLoadException.class, () -> verifiedLineupController.handleInitialization(new Lineup()));
        }


    }

}
