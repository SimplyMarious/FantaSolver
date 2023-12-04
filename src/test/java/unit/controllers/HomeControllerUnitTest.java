package unit.controllers;

import com.spme.fantasolver.controllers.AuthenticationManager;
import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.HomeController;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.entity.User;
import com.spme.fantasolver.ui.HomeStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class HomeControllerUnitTest {

    private HomeController homeController;
    private HomeStage mockHomeStage;
    private User testUser;

    @BeforeEach
    public void setUp() {
        homeController = HomeController.getInstance();
        mockHomeStage = mock(HomeStage.class);
        homeController.setHomeStage(mockHomeStage);

        testUser = new User("TestUser");
        AuthenticationManager.getInstance().signIn(testUser);
    }

    @Test
    public void testHandleInitializationWithExistingTeam() throws IOException {
        testUser.setTeam(new Team("TestTeam", Set.of(new Player("Player1"), new Player("Player2"))));

        homeController.handleInitialization();

        verify(mockHomeStage, times(1)).initializeStage();
        verify(mockHomeStage, times(1)).setManageTeamScreenVisible();
        verify(mockHomeStage, never()).setAddTeamScreenVisible();
        verify(mockHomeStage, times(1)).show();
    }

    @Test
    public void testHandleInitializationWithNoExistingTeam() throws IOException {
        homeController.handleInitialization();

        verify(mockHomeStage, times(1)).initializeStage();
        verify(mockHomeStage, times(1)).setAddTeamScreenVisible();
        verify(mockHomeStage, never()).setManageTeamScreenVisible();
        verify(mockHomeStage, times(1)).show();
    }

    @Test
    public void testHandleInitializationWithExceptionDuringInitialization() throws IOException {
        doThrow(new IOException()).when(mockHomeStage).initializeStage();

        try(MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)){
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger("HomeController")).thenReturn(mockedLogger);
            doNothing().when(mockedLogger).info(any(String.class));

            assertThrows(FXMLLoadException.class, () -> homeController.handleInitialization());
        }
    }
}