package integration;

import org.junit.jupiter.api.Test;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.RoleException;
import com.spme.fantasolver.entity.Slot;

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
