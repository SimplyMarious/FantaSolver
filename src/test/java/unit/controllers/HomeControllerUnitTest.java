package unit.controllers;

import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.HomeController;
import com.spme.fantasolver.ui.HomeStage;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class HomeControllerUnitTest {

    private HomeController homeController;
    private HomeStage mockHomeStage;

    @BeforeEach
    public void setUp() {
        homeController = HomeController.getInstance();
        mockHomeStage = mock(HomeStage.class);
        homeController.setHomeStage(mockHomeStage);
    }

    @Test
    public void testHandleInitializationWithExistingTeam() throws IOException {
        boolean doesTeamExist = true;

        homeController.handleInitialization(doesTeamExist);

        verify(mockHomeStage, times(1)).initializeStage();
        verify(mockHomeStage, times(1)).setManageTeamScreenVisible();
        verify(mockHomeStage, never()).setAddTeamScreenVisible();
        verify(mockHomeStage, times(1)).show();
    }

    @Test
    public void testHandleInitializationWithNoExistingTeam() throws IOException {
        boolean doesTeamExist = false;
        
        homeController.handleInitialization(doesTeamExist);

        verify(mockHomeStage, times(1)).initializeStage();
        verify(mockHomeStage, times(1)).setAddTeamScreenVisible();
        verify(mockHomeStage, never()).setManageTeamScreenVisible();
        verify(mockHomeStage, times(1)).show();
    }

    @Test
    public void testHandleInitializationWithExceptionDuringInitialization() throws IOException {
        boolean doesTeamExist = true;
        doThrow(new IOException()).when(mockHomeStage).initializeStage();

        try(MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)){
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger("HomeController")).thenReturn(mockedLogger);
            doNothing().when(mockedLogger).info(any(String.class));

            assertThrows(FXMLLoadException.class, () -> homeController.handleInitialization(doesTeamExist));
        }
    }
}