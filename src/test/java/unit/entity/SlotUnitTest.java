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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class SlotUnitTest {

    private Slot slot;

    @BeforeEach
    void setUp() {
        slot = new Slot((short)1);
    }

    @Test
    void testAddRoleWithSuitableRole() throws RoleException {
        Role newRole = Role.A;

        slot.addRole(newRole);

        assertTrue(slot.getRoles().contains(newRole));
    }

    @Test
    void testAddRoleWithNonSuitableRole() throws RoleException {
        Role newRole = Role.A;
        try (MockedStatic<Role> mockRole = mockStatic(Role.class)) {
            mockRole.when(() -> Role.checkNewRoleSuitability(newRole, slot.getRoles(), (short) 3)).thenReturn(false);
            slot.addRole(newRole);
            assertThat(slot.getRoles(), is(empty()));
        }
    }

    @Test
    void testAddRoleWithDuplicatedRoleException() throws RoleException {
        Role newRole = Role.A;

        slot.addRole(newRole);

        assertThrows(RoleException.class, ()->slot.addRole(newRole));
    }

    @Test
    void testAddRoleWithRoleLimitExceededException() throws RoleException {
        slot.addRole(Role.A);
        slot.addRole(Role.PC);
        slot.addRole(Role.T);

        assertThrows(RoleException.class, ()->slot.addRole(Role.W));
    }
    
    @Test
    void testAddRoleWithNullPointerException() {
        assertThrows(NullPointerException.class, ()->slot.addRole(null));
    }

    @Test
    void testSortSlotsByRolesSize() throws RoleException {
        Slot[] slots = new Slot[3];
        for (int i = 0; i < 3; i++) {
            slots[i] = new Slot((short)i);
        }
        slots[2].addRole(Role.A);
        slots[0].addRole(Role.PC);
        slots[0].addRole(Role.A);
        slots[1].addRole(Role.PC);
        slots[1].addRole(Role.A);
        slots[1].addRole(Role.T);

        Slot[] expectedSlots = new Slot[3];
        expectedSlots[0] = slots[2];
        expectedSlots[1] = slots[0];
        expectedSlots[2] = slots[1];

        Slot[] sortedSlots = Slot.sortSlotsByRolesSize(slots);

        for (int i = 0; i < 3; i++) {
            assertEquals(expectedSlots[i].getId(), sortedSlots[i].getId());
        }
    }

    @Test
    void testEqualsWithSameObject() {
        Slot slot = new Slot(0);
        assertEquals(slot, slot);
    }

    @Test
    void testEqualsWithNullObject() {
        Slot slot = new Slot(0);

        boolean result = slot.equals(null);

        assertFalse(result);
    }

    @Test
    void testEqualsWithDifferentClass() {
        Slot slot = new Slot(0);

        boolean result = slot.equals(new Object());

        assertFalse(result);
    }

    @Test
    void testEqualsWithDifferentClassAndNull() {
        Object aDifferentObjectType = null;

        boolean result = slot.equals(aDifferentObjectType);

        assertFalse(result);
    }

    @Test
    void testEqualsWithSameId() {
        Slot slot1 = new Slot(0);
        Slot slot2 = new Slot(0);

        assertEquals(slot1, slot2);
    }

    @Test
    void testEqualsWithDifferentId() {
        Slot slot1 = new Slot(0);
        Slot slot2 = new Slot(1);

        assertNotEquals(slot1, slot2);
    }
}