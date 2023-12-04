package unit.controllers;

import com.spme.fantasolver.controllers.AuthenticationManager;
import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.ManageTeamController;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.TeamDAO;
import com.spme.fantasolver.entity.*;
import com.spme.fantasolver.ui.ManageTeamStage;
import com.spme.fantasolver.utility.Notifier;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.*;


class ManageTeamControllerUnitTest {

    private ManageTeamController manageTeamController;
    private AuthenticationManager authenticationManager;
    @Mock
    private ManageTeamStage mockManageTeamStage;
    @Mock
    MockedStatic<Utility> mockUtility;
    @Mock
    private TeamDAO mockTeamDAO;
    @Mock
    private AuthenticationManager mockAuthenticationManager;

    @BeforeEach
    void setUp() {
        mockUtility = mockStatic(Utility.class);
        manageTeamController = ManageTeamController.getInstance();
        authenticationManager = AuthenticationManager.getInstance();
        mockManageTeamStage = mock(ManageTeamStage.class);
        manageTeamController.setManageTeamStage(mockManageTeamStage);
        mockTeamDAO = mock(TeamDAO.class);
        mockAuthenticationManager = mock(AuthenticationManager.class);
    }

    @AfterEach
    void clean(){
        mockUtility.close();
        mockManageTeamStage.close();
    }

    @Test
    void testHandleInitializationWithExistingTeam() {
        Set<Player> players = Set.of(new Player("TestPlayer", Set.of(Role.POR, Role.DC)));
        Team team = new Team("TestTeam", players);
        User user = new User("TestUser");
        user.setTeam(team);
        authenticationManager.signIn(user);

        manageTeamController.handleInitialization();

        verify(mockManageTeamStage, times(1)).loadPlayersInTable(players);
    }

    @Test
    void testHandleInitializationWithNotExistingTeam() {
        User user = new User("TestUser");
        authenticationManager.signIn(user);

        manageTeamController.handleInitialization();

        verify(mockManageTeamStage, never()).loadPlayersInTable(any(Set.class));
    }

    @Test
    void testHandleInitializationWithExceptionDuringInitialization() throws IOException {
        doThrow(new IOException()).when(mockManageTeamStage).initializeStage();

        try(MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)){
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger("ManageTeamController")).thenReturn(mockedLogger);
            doNothing().when(mockedLogger).info(any(String.class));

            assertThrows(FXMLLoadException.class, () -> manageTeamController.handleInitialization());
        }
    }

    @Test
    void testHandleTeamPropertyChangedWithValidTeamAndValidPlayersSize() {
        String teamName = "ValidTeam";
        int playersSize = 25;

        mockUtility.when(() -> Utility.checkStringValidity(
                                any(String.class),
                                any(Integer.class),
                                any(Integer.class))).thenReturn(true);

        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(true);

    }

    @Test
    void testHandleTeamPropertyChangedWithInvalidTeamAndValidPlayersSize() {
        String teamName = "Te";
        int playersSize = 25;

        mockUtility.when(() -> Utility.checkStringValidity(
                                any(String.class),
                                any(Integer.class),
                                any(Integer.class))).thenReturn(false);

        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(false);
    }

    @Test
    void testHandleTeamPropertyChangedWithValidTeamAndInvalidPlayersSize() {
        String teamName = "TestTeam";
        int playersSize = 4;

        mockUtility.when(() -> Utility.checkStringValidity(
                                any(String.class),
                                any(Integer.class),
                                any(Integer.class))).thenReturn(true);

        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(false);
    }

    @Test
    void testHandleTeamPropertyChangedWithInvalidTeamAndInvalidPlayersSize() {
        String teamName = "Te";
        int playersSize = 10;

        mockUtility.when(() -> Utility.checkStringValidity(
                                    any(String.class),
                                    any(Integer.class),
                                    any(Integer.class))).thenReturn(false);

        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(false);
    }

    @Test
    void testHandleTeamPropertyChangedWithValidTeamAnd0PlayerSize() {
        String teamName = "Te";
        int playersSize = 0;

        mockUtility.when(() -> Utility.checkStringValidity(
                                    any(String.class),
                                    any(Integer.class),
                                    any(Integer.class))).thenReturn(false);

        manageTeamController.handleTeamPropertyChanged(teamName, playersSize);

        verify(mockManageTeamStage, times(1)).setConfirmButtonAbility(false);
        verify(mockManageTeamStage, times(1)).setRemovePlayerButtonAbility(false);
    }

    @Test
    void testHandlePlayerPropertyChangedWithValidPlayerNameAndValidRoles() {
        String playerName = "John Doe";
        String firstRole = "DS";
        String secondRole = "DC";
        String thirdRole = "DD";

        mockUtility.when(() -> Utility.checkStringValidity(
                                any(String.class),
                                any(Integer.class),
                                any(Integer.class))).thenReturn(true);

        mockUtility.when(() -> Utility.areStringsDifferentFromEachOther(
                                any(List.class))).thenReturn(true);

        manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
        verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(true);
    }

    @Test
    void testHandlePlayerPropertyChangedWithValidPlayerNameAndValidFirstRoleAndOneUnvaluedRole() {
        String playerName = "John Doe";
        String firstRole = "DC";
        String secondRole = "Nessuno";
        String thirdRole = "DD";

        mockUtility.when(() -> Utility.checkStringValidity(
                                    any(String.class),
                                    any(Integer.class),
                                    any(Integer.class))).thenReturn(true);
        mockUtility.when(() -> Utility.areStringsDifferentFromEachOther(
                                    any(List.class))).thenReturn(true);

        manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
        verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(true);
    }

    @Test
    void testHandlePlayerPropertyChangedWithInvalidPlayerNameAndValidRoles() {
        String playerName = "A";
        String firstRole = "DS";
        String secondRole = "DC";
        String thirdRole = "DD";

        mockUtility.when(() -> Utility.checkStringValidity(
                                    any(String.class),
                                    any(Integer.class),
                                    any(Integer.class))).thenReturn(false);
        mockUtility.when(() -> Utility.areStringsDifferentFromEachOther(
                                    any(List.class))).thenReturn(true);

        manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
        verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(false);
    }

    @Test
    void testHandlePlayerPropertyChangedWithValidPlayerNameAndInvalidFirstRole() {
        String playerName = "John Doe";
        String firstRole = "Nessuno";
        String secondRole = "DC";
        String thirdRole = "DD";

        mockUtility.when(() -> Utility.checkStringValidity(
                                    any(String.class),
                                    any(Integer.class),
                                    any(Integer.class))).thenReturn(true);
        mockUtility.when(() -> Utility.areStringsDifferentFromEachOther(
                                    any(List.class))).thenReturn(true);

        manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
        verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(false);
    }

    @Test
    void testHandlePlayerPropertyChangedWithValidPlayerNameAndDuplicateRoles() {
        String playerName = "John Doe";
        String firstRole = "DC";
        String secondRole = "DC";
        String thirdRole = "DD";

        mockUtility.when(() -> Utility.checkStringValidity(
                                    any(String.class),
                                    any(Integer.class),
                                    any(Integer.class))).thenReturn(true);
        mockUtility.when(() -> Utility.areStringsDifferentFromEachOther
                                    (any(List.class))).thenReturn(false);

        manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
        verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(false);
    }

    @Test
    void testHandlePlayerPropertyChangedWithInvalidPlayerNameAndDuplicateRoles() {
        String playerName = "A";
        String firstRole = "DC";
        String secondRole = "DC";
        String thirdRole = "DD";

        mockUtility.when(() -> Utility.checkStringValidity(
                                    any(String.class),
                                    any(Integer.class),
                                    any(Integer.class))).thenReturn(false);
        mockUtility.when(() -> Utility.areStringsDifferentFromEachOther(
                                    any(List.class))).thenReturn(false);

        manageTeamController.handlePlayerPropertyChanged(playerName, firstRole, secondRole, thirdRole);
        verify(mockManageTeamStage, times(1)).setAddPlayerButtonAbility(false);
    }

    @Test
    void testHandlePressedAddPlayerButtonWithPlayerNotInList(){
        String playerName = "John Doe";
        String firstRole = "DC";
        String secondRole = "DD";
        String thirdRole = "Nessuno";

        when(mockManageTeamStage.getPlayers()).thenReturn(List.of(new Player("Rossi")));

        manageTeamController.handlePressedAddPlayerButton(playerName, firstRole, secondRole, thirdRole);

        verify(mockManageTeamStage, times(1)).addPlayerToTableView(any(Player.class));
        verify(mockManageTeamStage, never()).highlightPlayerInTableView(any(Player.class));
    }

    @Test
    void testHandlePressedAddPlayerButtonWithPlayerAlreadyInList(){
        String playerName = "John Doe";
        String firstRole = "DC";
        String secondRole = "DD";
        String thirdRole = "Nessuno";

        when(mockManageTeamStage.getPlayers()).thenReturn(List.of(
                new Player("John Doe", Set.of(Role.DC, Role.DD))));

        manageTeamController.handlePressedAddPlayerButton(playerName, firstRole, secondRole, thirdRole);

        verify(mockManageTeamStage, never()).addPlayerToTableView(any(Player.class));
        verify(mockManageTeamStage, times(1)).highlightPlayerInTableView(any(Player.class));
    }

    @Test
    void testHandlePressedAddPlayerButtonWithInvalidRoles(){
        String playerName = "John Doe";
        String firstRole = "DC";
        String secondRole = "DC";
        String thirdRole = "DD";

        try(MockedStatic<Logger> loggerMockedStatic = mockStatic(Logger.class)) {
            Logger mockedLogger = mock(Logger.class);
            loggerMockedStatic.when(() -> Logger.getLogger("ManageTeamController")).thenReturn(mockedLogger);

            manageTeamController.handlePressedAddPlayerButton(playerName, firstRole, secondRole, thirdRole);
            verify(mockedLogger).info(anyString());
        }
    }

    @Test
    void testHandlePressedConfirmButtonWithSuccessfulUpdate() {
        String teamName = "TestTeam";
        List<Player> players = List.of(new Player("Player1"), new Player("Player2"));

        try(MockedStatic<DAOFactory> daoFactoryMockedStatic = mockStatic(DAOFactory.class)) {
            daoFactoryMockedStatic.when(DAOFactory::getTeamDAO).thenReturn(mockTeamDAO);
            when(mockTeamDAO.updateTeam(any(Team.class), any(User.class))).thenReturn(true);

            try(MockedStatic<AuthenticationManager> authenticationManagerMockedStatic =
                    mockStatic(AuthenticationManager.class)){
                authenticationManagerMockedStatic.when(AuthenticationManager::getInstance).
                        thenReturn(mockAuthenticationManager);

                try (MockedStatic<Notifier> notifierMockedStatic = mockStatic(Notifier.class)) {
                    manageTeamController.handlePressedConfirmButton(teamName, players);

                    verify(mockAuthenticationManager, times(1)).getUser();
                }
            }
        }
    }

    @Test
    void testHandlePressedConfirmButtonWithFailingUpdate() {
        String teamName = "TestTeam";
        List<Player> players = List.of(new Player("Player1"), new Player("Player2"));
        try (MockedStatic<DAOFactory> daoFactoryMockedStatic = mockStatic(DAOFactory.class)) {
            daoFactoryMockedStatic.when(DAOFactory::getTeamDAO).thenReturn(mockTeamDAO);
            when(mockTeamDAO.updateTeam(any(Team.class), any(User.class))).thenReturn(false);

            try (MockedStatic<Notifier> notifierMockedStatic = mockStatic(Notifier.class)) {
                manageTeamController.handlePressedConfirmButton(teamName, players);
                verify(mockAuthenticationManager, never()).getUser();
                notifierMockedStatic.verify(() ->
                        Notifier.notifyError(anyString(), anyString()), times(1));
            }
        }
    }
}
