package unit.entity;

import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.RoleException;
import com.spme.fantasolver.entity.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockStatic;

public class SlotUnitTest {

    private Slot slot;

    @BeforeEach
    public void setUp() {
        slot = new Slot((short)1);
    }

    @Test
    public void testAddRoleWithSuitableRole() throws RoleException {
        Role newRole = Role.A;

        slot.addRole(newRole);

        assertTrue(slot.getRoles().contains(newRole));
    }

    @Test
    public void testAddRoleWithNonSuitableRole() throws RoleException {
        Role newRole = Role.A;
        try (MockedStatic<Role> mockRole = mockStatic(Role.class)) {
            mockRole.when(() -> Role.checkNewRoleSuitability(newRole, slot.getRoles(), (short) 3)).thenReturn(false);
            slot.addRole(newRole);
            assertThat(slot.getRoles(), is(empty()));
        }
    }

    @Test
    public void testAddRoleWithDuplicatedRoleException() throws RoleException {
        Role newRole = Role.A;

        slot.addRole(newRole);

        assertThrows(RoleException.class, ()->slot.addRole(newRole));
    }

    @Test
    public void testAddRoleWithRoleLimitExceededException() throws RoleException {
        slot.addRole(Role.A);
        slot.addRole(Role.PC);
        slot.addRole(Role.T);

        assertThrows(RoleException.class, ()->slot.addRole(Role.W));
    }
    
    @Test
    public void testAddRoleWithNullPointerException() {
        assertThrows(NullPointerException.class, ()->slot.addRole(null));
    }
}