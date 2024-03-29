package unit.entity;

import com.spme.fantasolver.entity.Formation;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.RoleException;
import com.spme.fantasolver.entity.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FormationUnitTest {
    private Formation formation;

    @BeforeEach
    void setUp(){
        formation = new Formation("TestFormation");
    }

    @Test
    void testSetSlot(){
        Slot slot = new Slot((short)1);

        formation.setSlot(slot);
        Slot retrievedSlot = formation.getSlots()[slot.getId()];

        assertThat(retrievedSlot, is(equalTo(slot)));
    }

    @Test
    void testAddRole() throws RoleException {
        Slot slot = new Slot((short)2);
        formation.setSlot(slot);

        formation.addRole(slot.getId(), Role.A);

        assertTrue(formation.getSlots()[slot.getId()].getRoles().contains(Role.A));
    }

    @Test
    void addRoleWithDuplicateRolesExceptionTest() throws RoleException {
        Slot slot = new Slot((short)2);
        formation.setSlot(slot);
        formation.addRole(slot.getId(), Role.A);

        assertThrows(RoleException.class, ()-> formation.addRole(slot.getId(), Role.A));
    }

    @Test
    void testAddRoleWithRoleLimitExceededException() throws RoleException {
        Slot slot = new Slot((short)2);
        formation.setSlot(slot);
        formation.addRole(slot.getId(), Role.A);
        formation.addRole(slot.getId(), Role.PC);
        formation.addRole(slot.getId(), Role.T);

        assertThrows(RoleException.class, ()-> formation.addRole(slot.getId(), Role.W));
    }

    @Test
    void testAddRoleWithNullPointerException() {
        Slot slot = new Slot((short)2);
        formation.setSlot(slot);

        assertThrows(NullPointerException.class, ()-> formation.addRole((short) 2, null));
    }
}
