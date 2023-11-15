package unit;

import com.spme.fantasolver.entity.*;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SlotUnitTest {

    @Test
    public void testAddRoleWithFreshSlot() throws RoleException {
        Slot slot = new Slot();
        slot.addRole(Role.POR);
        assertThat(slot.getRoles(), contains(Role.POR));
    }

    @Test
    public void testAddRoleWithSlotAlreadyContainingADifferentRole() throws RoleException {
        Slot slot = new Slot();
        slot.addRole(Role.DC);
        slot.addRole(Role.DD);
        assertThat(slot.getRoles(), containsInAnyOrder(Role.DC, Role.DD));
    }

    @Test
    public void testAddRoleWithSlotAlreadyContainingTwoDifferentRoles() throws RoleException {
        Slot slot = new Slot();
        slot.addRole(Role.DC);
        slot.addRole(Role.DD);
        slot.addRole(Role.DS);
        assertThat(slot.getRoles(), containsInAnyOrder(Role.DC, Role.DD, Role.DS));
    }

    @Test
    public void testAddRoleWithNullRole() {
        Slot slot = new Slot();
        assertThrows(NullPointerException.class, () -> slot.addRole(null));
    }

    @Test
    public void testAddRoleWithSlotAlreadyContainingTheNewRole() throws RoleException {
        Slot slot = new Slot();
        slot.addRole(Role.C);
        assertThrows(DuplicateRoleException.class, () -> slot.addRole(Role.C));
    }

    @Test
    public void testAddRoleWithSlotAlreadyContainingThreeRoles() throws RoleException {
        Slot slot = new Slot();
        slot.addRole(Role.DC);
        slot.addRole(Role.DD);
        slot.addRole(Role.DS);
        assertThrows(RoleLimitExceededException.class, () -> slot.addRole(Role.E));
    }
}