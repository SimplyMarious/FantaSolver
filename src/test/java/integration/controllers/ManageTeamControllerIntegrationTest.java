package integration.controllers;

import com.spme.fantasolver.controllers.ManageTeamController;
import com.spme.fantasolver.ui.ManageTeamStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.logging.Logger;
import java.util.stream.Stream;

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
        String teamName = "TeamName";
        int playersSize = 25;

        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(true);
    }

    @ParameterizedTest(name = "Text {index} ==> validity with: teamName = {0}, playersSize = {1})")
    @MethodSource("addInputProvider")
    void testHandleTeamPropertyChangedWithInvalidTeamNameAndValidPlayersSize(String teamName, int playersSize){
        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(false);
    }


    static Stream<Arguments> addInputProvider() {
        return Stream.of(
                Arguments.of("Te", 25),
                Arguments.of("TeamName", 8),
                Arguments.of("Te", 8)
        );
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
