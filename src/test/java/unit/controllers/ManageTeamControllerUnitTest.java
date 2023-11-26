package unit.controllers;

import com.spme.fantasolver.controllers.AuthenticationManager;
import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.ManageTeamController;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.entity.User;
import com.spme.fantasolver.ui.ManageTeamStage;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.lang.reflect.*;


public class ManageTeamControllerUnitTest {

    private ManageTeamController manageTeamController;
    private AuthenticationManager authenticationManager;
    private ManageTeamStage mockManageTeamStage;

    @BeforeEach
    public void setUp() {
        manageTeamController = ManageTeamController.getInstance();
        authenticationManager = AuthenticationManager.getInstance();
        mockManageTeamStage = mock(ManageTeamStage.class);
        manageTeamController.setManageTeamStage(mockManageTeamStage);
    }

    @Test
    public void testHandleInitializationWithExistingTeam() {
        Set<Player> players = Set.of(new Player("TestPlayer", Set.of(Role.POR, Role.DC)));
        Team team = new Team("TestTeam");
        team.setPlayers(players);
        User user = new User("TestUser");
        user.setTeam(team);
        authenticationManager.signIn(user);

        manageTeamController.handleInitialization();

        verify(mockManageTeamStage, times(1)).loadPlayersInTable(players);
    }

    @Test
    public void testHandleInitializationWithNotExistingTeam() {
        User user = new User("TestUser");
        authenticationManager.signIn(user);

        manageTeamController.handleInitialization();

        verify(mockManageTeamStage, never()).loadPlayersInTable(any(Set.class));
    }

    @Test
    public void testHandleInitializationWithExceptionDuringInitialization() throws IOException {
        doThrow(new IOException()).when(mockManageTeamStage).initializeStage();

        try(MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)){
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger("ManageTeamController")).thenReturn(mockedLogger);
            doNothing().when(mockedLogger).info(any(String.class));

            assertThrows(FXMLLoadException.class, () -> manageTeamController.handleInitialization());
        }
    }

    @Test
    public void testHandleTeamPropertyChangedWithValidTeamAndValidPlayersSize() {
        String teamName = "ValidTeam";
        int playersSize = 25;

        try(MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)){
            mockedUtility.when(() ->
                            Utility.checkStringValidity(any(String.class), any(Integer.class), any(Integer.class))).
                            thenReturn(true);

            manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

            verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(true);
        }
    }

    @Test
    public void testHandleTeamPropertyChangedWithInvalidTeamAndValidPlayersSize() {
        String teamName = "Te";
        int playersSize = 25;

        try(MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)){
            mockedUtility.when(() ->
                            Utility.checkStringValidity(any(String.class), any(Integer.class), any(Integer.class))).
                    thenReturn(false);

            manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

            verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(false);
        }
    }

    @Test
    public void testHandleTeamPropertyChangedWithValidTeamAndInvalidPlayersSize() {
        String teamName = "TestTeam";
        int playersSize = 4;

        try(MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)){
            mockedUtility.when(() ->
                            Utility.checkStringValidity(any(String.class), any(Integer.class), any(Integer.class))).
                    thenReturn(true);

            manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

            verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(false);
        }
    }

    @Test
    public void testHandleTeamPropertyChangedWithInvalidTeamAndInvalidPlayersSize() {
        String teamName = "Te";
        int playersSize = 10;

        try(MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)){
            mockedUtility.when(() ->
                            Utility.checkStringValidity(any(String.class), any(Integer.class), any(Integer.class))).
                    thenReturn(false);

            manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

            verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(false);
        }
    }

    @Test
    public void testHandlePlayerPropertyChangedWithValidPlayerNameAndValidRoles() {
        String playerName = "John Doe";
        String firstRole = "DS";
        String secondRole = "DC";
        String thirdRole = "DD";

        try(MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)){
            mockedUtility.when(() ->
                            Utility.checkStringValidity(any(String.class), any(Integer.class), any(Integer.class))).
                            thenReturn(true);
            mockedUtility.when(() ->
                            Utility.areStringsDifferentFromEachOther(any(List.class))).
                            thenReturn(true);

            manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
            verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(true);
        }
    }

    @Test
    public void testHandlePlayerPropertyChangedWithValidPlayerNameAndValidFirstRoleAndOneUnvaluedRole() {
        String playerName = "John Doe";
        String firstRole = "DC";
        String secondRole = "Nessuno";
        String thirdRole = "DD";

        try(MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)){
            mockedUtility.when(() ->
                            Utility.checkStringValidity(any(String.class), any(Integer.class), any(Integer.class))).
                    thenReturn(true);
            mockedUtility.when(() ->
                            Utility.areStringsDifferentFromEachOther(any(List.class))).
                    thenReturn(true);

            manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
            verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(true);
        }
    }

    @Test
    public void testHandlePlayerPropertyChangedWithInvalidPlayerNameAndValidRoles() {
        String playerName = "A";
        String firstRole = "DS";
        String secondRole = "DC";
        String thirdRole = "DD";

        try(MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)){
            mockedUtility.when(() ->
                            Utility.checkStringValidity(any(String.class), any(Integer.class), any(Integer.class))).
                    thenReturn(false);
            mockedUtility.when(() ->
                            Utility.areStringsDifferentFromEachOther(any(List.class))).
                    thenReturn(true);

            manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
            verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(false);
        }
    }

    @Test
    public void testHandlePlayerPropertyChangedWithValidPlayerNameAndInvalidFirstRole() {
        String playerName = "John Doe";
        String firstRole = "Nessuno";
        String secondRole = "DC";
        String thirdRole = "DD";

        try(MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)){
            mockedUtility.when(() ->
                            Utility.checkStringValidity(any(String.class), any(Integer.class), any(Integer.class))).
                    thenReturn(true);
            mockedUtility.when(() ->
                            Utility.areStringsDifferentFromEachOther(any(List.class))).
                    thenReturn(true);

            manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
            verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(false);
        }
    }

    @Test
    public void testHandlePlayerPropertyChangedWithValidPlayerNameAndDuplicateRoles() {
        String playerName = "John Doe";
        String firstRole = "DC";
        String secondRole = "DC";
        String thirdRole = "DD";

        try(MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)){
            mockedUtility.when(() ->
                            Utility.checkStringValidity(any(String.class), any(Integer.class), any(Integer.class))).
                    thenReturn(true);
            mockedUtility.when(() ->
                            Utility.areStringsDifferentFromEachOther(any(List.class))).
                    thenReturn(false);

            manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
            verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(false);
        }
    }

    @Test
    public void testHandlePlayerPropertyChangedWithInvalidPlayerNameAndDuplicateRoles() {
        String playerName = "A";
        String firstRole = "DC";
        String secondRole = "DC";
        String thirdRole = "DD";

        try(MockedStatic<Utility> mockedUtility = mockStatic(Utility.class)){
            mockedUtility.when(() ->
                            Utility.checkStringValidity(any(String.class), any(Integer.class), any(Integer.class))).
                    thenReturn(false);
            mockedUtility.when(() ->
                            Utility.areStringsDifferentFromEachOther(any(List.class))).
                    thenReturn(false);

            manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
            verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(false);
        }
    }
}
