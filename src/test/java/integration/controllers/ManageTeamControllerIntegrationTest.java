package integration.controllers;

import com.spme.fantasolver.controllers.ManageTeamController;
import com.spme.fantasolver.ui.ManageTeamStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.logging.Logger;

import static org.mockito.Mockito.*;

class ManageTeamControllerIntegrationTest {
    private ManageTeamController manageTeamController;
    @Mock
    private ManageTeamStage mockManageTeamStage;

    @BeforeEach
    void setUp(){
        manageTeamController = ManageTeamController.getInstance();
        mockManageTeamStage = mock(ManageTeamStage.class);
        manageTeamController.setManageTeamStage(mockManageTeamStage);
    }


    @Test
    void testHandleTeamPropertyChangedWithValidTeamNameAndValidPlayersSize(){
        String teamName = "ValidTeam";
        int playersSize = 25;

        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(true);
    }

    @Test
    void testHandleTeamPropertyChangedWithInvalidTeamNameAndValidPlayersSize(){
        String teamName = "Te";
        int playersSize = 25;

        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(false);
    }

    @Test
    void testHandleTeamPropertyChangedWithValidTeamNameAndInvalidPlayersSize(){
        String teamName = "TeamName";
        int playersSize = 8;

        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(false);
    }

    @Test
    void testHandleTeamPropertyChangedWithInvalidTeamNameAndInvalidPlayersSize(){
        String teamName = "Te";
        int playersSize = 8;

        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(false);
    }

    @Test
    void testHandleTeamPropertyChangedWithIllegalArgumentExceptionThrown(){
        String teamName = null;
        int playersSize = 25;

        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        try(MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)) {
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger("ManageTeamController")).thenReturn(mockedLogger);

            manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

            verify(mockedLogger, times(1)).info("Invalid argument");
        }
    }
}
