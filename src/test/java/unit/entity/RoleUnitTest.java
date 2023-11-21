package unit.entity;

import com.spme.fantasolver.entity.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

public class RoleUnitTest {
    private static final short MAX_ROLES = 3;

    @Test
    public void testCheckNewRoleSuitabilityWithEmptyRolesSet() throws RoleException {
        Set<Role> roles = new HashSet<>();
        assertTrue(Role.checkNewRoleSuitability(Role.DC, roles, MAX_ROLES));
    }

    @Test
    public void testCheckNewRoleSuitabilityWithRolesSetAlreadyContainingADifferentRole() throws RoleException {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.DC);
        assertTrue(Role.checkNewRoleSuitability(Role.DD, roles, MAX_ROLES));
    }

    @Test
    public void testCheckNewRoleSuitabilityWithRolesSetAlreadyContainingTwoDifferentRoles() throws RoleException {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.DC);
        roles.add(Role.DS);
        assertTrue(Role.checkNewRoleSuitability(Role.DD, roles, MAX_ROLES));
    }

    @Test
    public void testCheckNewRoleSuitabilityWithNullRole() {
        Set<Role> roles = new HashSet<>();
        assertThrows(NullPointerException.class, () -> Role.checkNewRoleSuitability(null, roles, MAX_ROLES));
    }

    @Test
    public void testCheckNewRoleSuitabilityWithNullRolesSet() {
        assertThrows(NullPointerException.class, () -> Role.checkNewRoleSuitability(Role.A, null, MAX_ROLES));
    }

    @Test
    public void testCheckNewRoleSuitabilityWithRolesSetAlreadyContainingTheNewRole() throws RoleException {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.DC);
        assertThrows(DuplicateRoleException.class, () -> Role.checkNewRoleSuitability(Role.DC, roles, MAX_ROLES));
    }

    @Test
    public void testCheckNewRoleSuitabilityWithRolesSetAlreadyContainingThreeRoles() throws RoleException {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.DC);
        roles.add(Role.DS);
        roles.add(Role.DD);
        assertThrows(RoleLimitExceededException.class, () -> Role.checkNewRoleSuitability(Role.M, roles, MAX_ROLES));
    }
}
