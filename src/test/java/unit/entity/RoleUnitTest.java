package unit.entity;

import com.spme.fantasolver.entity.*;
import com.spme.fantasolver.utility.Utility;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

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
    public void testCheckNewRoleSuitabilityWithRolesSetAlreadyContainingTheNewRole(){
        Set<Role> roles = new HashSet<>();
        roles.add(Role.DC);
        assertThrows(DuplicateRoleException.class, () -> Role.checkNewRoleSuitability(Role.DC, roles, MAX_ROLES));
    }

    @Test
    public void testCheckNewRoleSuitabilityWithRolesSetAlreadyContainingThreeRoles(){
        Set<Role> roles = new HashSet<>();
        roles.add(Role.DC);
        roles.add(Role.DS);
        roles.add(Role.DD);
        assertThrows(RoleLimitExceededException.class, () -> Role.checkNewRoleSuitability(Role.M, roles, MAX_ROLES));
    }

    @Test
    public void getFormattedRolesShouldThrowExceptionForNullRoles() {
        assertThrows(IllegalArgumentException.class, () -> Role.getFormattedRoles(null));
    }

    @Test
    public void getFormattedRolesShouldThrowExceptionForEmptyRoles() {
        Set<Role> roles = new HashSet<>();
        assertThrows(IllegalArgumentException.class, () -> Role.getFormattedRoles(roles));
    }

    @Test
    public void getFormattedRolesWithValidRoles() {
        Set<Role> roles = Set.of(Role.DC, Role.DD);
        String formattedRoles = "DC, DD";

        try(MockedStatic<Utility> utilityMockedStatic = mockStatic(Utility.class)) {
            utilityMockedStatic.when(() -> Utility.getFormattedStrings(anyList())).thenReturn(formattedRoles);

            SimpleStringProperty result = Role.getFormattedRoles(roles);
            assertThat(result.get(), is("DC, DD"));
        }

    }




}
