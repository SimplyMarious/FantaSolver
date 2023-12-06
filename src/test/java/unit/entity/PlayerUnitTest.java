package unit.entity;

import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.RoleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class PlayerUnitTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void testAddRoleWithSuitableRole() throws RoleException {
        Role newRole = Role.A;

        player.addRole(newRole);

        assertTrue(player.getRoles().contains(newRole));
    }

    @Test
    void testAddRoleWithNonSuitableRole() throws RoleException {
        Role newRole = Role.A;
        try (MockedStatic<Role> mockRole = mockStatic(Role.class)) {
            mockRole.when(() -> Role.checkNewRoleSuitability(newRole, player.getRoles(), (short) 3)).thenReturn(false);
            player.addRole(newRole);
            assertThat(player.getRoles(), is(empty()));
        }
    }

    @Test
    void testAddRoleWithDuplicatedRoleException() throws RoleException {
        Role newRole = Role.A;

        player.addRole(newRole);

        assertThrows(RoleException.class, ()->player.addRole(newRole));
    }

    @Test
    void testAddRoleWithRoleLimitExceededException() throws RoleException {
        player.addRole(Role.A);
        player.addRole(Role.PC);
        player.addRole(Role.T);

        assertThrows(RoleException.class, ()->player.addRole(Role.W));
    }

    @Test
    void testAddRoleWithNullPointerException() {
        assertThrows(NullPointerException.class, ()->player.addRole(null));
    }

    @Test
    void testEqualsWithSamePlayers() {
        boolean result = player.equals(player);
        assertTrue(result);
    }

    @Test
    void testEqualsWithDifferentPlayers() {
        Player differentPlayer = new Player("Castolo");
        boolean result = player.equals(differentPlayer);
        assertFalse(result);
    }

    @Test
    void testEqualsWithNull() {
        boolean result = player.equals(null);
        assertFalse(result);
    }

    @Test
    void testEqualsWithDifferentClassNotNull() {
        Object aDifferentObjectType = new Object();

        boolean result = player.equals(aDifferentObjectType);

        assertFalse(result);
    }

    @Test
    void testEqualsWithDifferentClassNull() {
        Object aDifferentObjectType = null;

        boolean result = player.equals(aDifferentObjectType);

        assertFalse(result);
    }

    @Test
    void testToString() throws RoleException {
        player.setName("Castolo");
        player.addRole(Role.A);

        String expectedString = "Player{name='Castolo', roles=[A]}";

        String playerString = player.toString();

        assertThat(playerString, is(equalTo(expectedString)));
    }

    @Test
    void testCompareTo() {
        Player player1 = new Player("Player1", new HashSet<>(List.of(Role.POR)));
        Player player2 = new Player("Player2", new HashSet<>(List.of(Role.DC)));
        Player player3 = new Player("Player3", new HashSet<>(List.of(Role.M)));
        Player[] players = {player3, player1, player2};

        Arrays.sort(players);

        assertEquals(player1, players[0]);
        assertEquals(player2, players[1]);
        assertEquals(player3, players[2]);
    }
}
