package unit.controllers;

import com.spme.fantasolver.controllers.AuthenticationManager;
import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.ProposeLineupController;
import com.spme.fantasolver.controllers.ProposeLineupController;
import com.spme.fantasolver.ui.ProposeLineupStage;
import com.spme.fantasolver.ui.ProposeLineupStage;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class ProposeLineupControllerUnitTest {
    private ProposeLineupController proposeLineupController;

    @Mock
    private ProposeLineupStage mockProposeLineupStage;
    @Mock
    MockedStatic<Utility> mockUtility;

    @BeforeEach
    public void setUp() {
        mockUtility = mockStatic(Utility.class);
        proposeLineupController = ProposeLineupController.getInstance();
        mockProposeLineupStage = mock(ProposeLineupStage.class);
        proposeLineupController.setProposeLineupStage(mockProposeLineupStage);
    }

    @Test
    public void testHandleInitializationWithExceptionDuringInitialization() throws IOException {
        doThrow(new IOException()).when(mockProposeLineupStage).initializeStage();

        try(MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)){
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger("ProposeLineupController")).thenReturn(mockedLogger);
            doNothing().when(mockedLogger).info(any(String.class));

            assertThrows(FXMLLoadException.class, () -> proposeLineupController.handleInitialization());
        }
    }
}
