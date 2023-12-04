package unit.entity;

import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.RoleException;
import com.spme.fantasolver.entity.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SlotUnitTest {

    private Slot slot;

    @BeforeEach
    public void setUp() {
        slot = new Slot((short)1);
    }

    @Test
    public void testAddRole() throws RoleException {
        Role newRole = Role.A;

        slot.addRole(newRole);

        assertTrue(slot.getRoles().contains(newRole));
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