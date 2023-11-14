package integration;

import org.junit.jupiter.api.Test;
import com.spme.fantasolver.Role;
import com.spme.fantasolver.RoleException;
import com.spme.fantasolver.Slot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class SlotIntegrationTest {

    @Test
    public void testAddRoleWithFreshSlot() throws RoleException {
        Slot slot = new Slot();
        slot.addRole(Role.POR);
        assertThat(slot.getRoles(), contains(Role.POR));
    }
}
