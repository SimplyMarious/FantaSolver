package unit.entity;

import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.RoleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class PlayerUnitTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player();
    }

    @Test
    public void testAddRoleWithSuitableRole() throws RoleException {
        Role newRole = Role.A;

        player.addRole(newRole);

        assertTrue(player.getRoles().contains(newRole));
    }

    @Test
    public void testAddRoleWithNonSuitableRole() throws RoleException {
        Role newRole = Role.A;
        try (MockedStatic<Role> mockRole = mockStatic(Role.class)) {
            mockRole.when(() -> Role.checkNewRoleSuitability(newRole, player.getRoles(), (short) 3)).thenReturn(false);
            player.addRole(newRole);
            assertThat(player.getRoles(), is(empty()));
        }
    }

    @Test
    public void testAddRoleWithDuplicatedRoleException() throws RoleException {
        Role newRole = Role.A;

        player.addRole(newRole);

        assertThrows(RoleException.class, ()->player.addRole(newRole));
    }

    @Test
    public void testAddRoleWithRoleLimitExceededException() throws RoleException {
        player.addRole(Role.A);
        player.addRole(Role.PC);
        player.addRole(Role.T);

        assertThrows(RoleException.class, ()->player.addRole(Role.W));
    }

    @Test
    public void testAddRoleWithNullPointerException() {
        assertThrows(NullPointerException.class, ()->player.addRole(null));
    }

    @Test
    public void testEqualsWithSamePlayers() {
        boolean result = player.equals(player);
        assertTrue(result);
    }

    @Test
    public void testEqualsWithDifferentPlayers() {
        Player differentPlayer = new Player("Castolo");
        boolean result = player.equals(differentPlayer);
        assertFalse(result);
    }

    @Test
    public void testEqualsWithNull() {
        boolean result = player.equals(null);
        assertFalse(result);
    }

    @Test
    public void testEqualsWithDifferentClassNotNull() {
        String aDifferentObjectType = new String();

        boolean result = player.equals(aDifferentObjectType);

        assertFalse(result);
    }

    @Test
    public void testEqualsWithDifferentClassNull() {
        String aDifferentObjectType = null;

        boolean result = player.equals(aDifferentObjectType);

        assertFalse(result);
    }

    @Test
    public void testToString() throws RoleException {
        player.setName("Castolo");
        player.addRole(Role.A);

        String expectedString = "Player{name='Castolo', roles=[A]}";

        String playerString = player.toString();

        assertThat(playerString, is(equalTo(expectedString)));
    }
}
