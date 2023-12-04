package unit.entity;

import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.RoleException;
import com.spme.fantasolver.entity.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    public void testSortSlotsByRolesSize() throws RoleException {
        Slot[] slots = new Slot[3];
        for (int i = 0; i < 3; i++) {
            slots[i] = new Slot((short)i);
        }
        slots[1].addRole(Role.A);
        slots[2].addRole(Role.PC);
        slots[2].addRole(Role.A);
        slots[0].addRole(Role.PC);
        slots[0].addRole(Role.A);
        slots[0].addRole(Role.T);

        Slot[] expectedSlots = new Slot[3];
        expectedSlots[0] = slots[1];
        expectedSlots[1] = slots[2];
        expectedSlots[2] = slots[0];

        Slot[] sortedSlots = Slot.sortSlotsByRolesSize(slots);

        for (int i = 0; i < 3; i++) {
            assertEquals(expectedSlots[i].getId(), sortedSlots[i].getId());
        }

    }
}