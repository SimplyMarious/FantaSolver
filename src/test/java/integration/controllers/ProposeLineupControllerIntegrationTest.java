package integration.controllers;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.controllers.ProposeLineupController;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.entity.Lineup;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.ui.StageFactory;
import com.spme.fantasolver.ui.VerifiedLineupStage;
import com.spme.fantasolver.utility.Notifier;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Properties;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ProposeLineupControllerIntegrationTest {

    private static ProposeLineupController proposeLineupController;
    private static Player[] players;
    private static StageFactory mockStageFactory;

    @BeforeAll
    static void initialize() {
        proposeLineupController = ProposeLineupController.getInstance();

        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));

        players = new Player[11];
        Role[] roles = new Role[]{
                Role.POR, Role.DD, Role.DC, Role.DC, Role.DS,
                Role.W, Role.M, Role.C, Role.W, Role.A, Role.PC
        };

        for (int i = 0; i < 11; i++) {
            players[i] = new Player();
            players[i].setName("Player"+i);
            players[i].setRoles(Set.of(roles[i]));
        }

        Lineup mockLineup = mock(Lineup.class);
        VerifiedLineupStage mockVerifiedLineupStage = mock(VerifiedLineupStage.class);
        mockStageFactory = mock(StageFactory.class);
        proposeLineupController.setStageFactory(mockStageFactory);
        when(mockStageFactory.createVerifiedLineupStage(mockLineup)).thenReturn(mockVerifiedLineupStage);
    }

    @Test
    void testHandlePressedVerifyLineupButtonWithLineupNotNull(){
        DAOFactory.resetFactory();

        proposeLineupController.handlePressedVerifyLineupButton(Set.of(players));

        verify(mockStageFactory, times(1)).createVerifiedLineupStage(any(Lineup.class));
    }

    @Test
    void testHandlePressedVerifyLineupButtonWithLineupNull() {
        DAOFactory.resetFactory();

        MockedStatic<Notifier> mockNotifier = mockStatic(Notifier.class);

        proposeLineupController.handlePressedVerifyLineupButton(Set.of());

        mockNotifier.verify(() ->
                Notifier.notifyInfo(anyString(), anyString()), times(1));

        mockNotifier.close();
    }
}
