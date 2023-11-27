package unit.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import com.spme.fantasolver.dao.*;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.io.IOException;
import static org.mockito.Mockito.*;

public class DAOFactoryUnitTest {

    @Test
    public void testGetTeamDAOWithMySQLAsTeamDAOValueInProperties() {
        try (MockedStatic<Utility> utilityMock = mockStatic(Utility.class)) {
            utilityMock.when(() -> Utility.getValueFromProperties("teamDAO")).thenReturn("MySQL");

            TeamDAO result = DAOFactory.getTeamDAO();

            assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
        }
    }

    @Test
    public void testGetTeamDAOWithAnyOtherStringAsTeamDAOValueInProperties() {
        try (MockedStatic<Utility> utilityMock = mockStatic(Utility.class)) {
            utilityMock.when(() -> Utility.getValueFromProperties("teamDAO")).thenReturn("SomeDBMS");

            TeamDAO result = DAOFactory.getTeamDAO();

            assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
        }
    }

    @Test
    public void testGetUserDAOWithMySQLAsUserDAOValueInProperties() {
        try (MockedStatic<Utility> utilityMock = mockStatic(Utility.class)) {
            utilityMock.when(() -> Utility.getValueFromProperties("userDAO")).thenReturn("MySQL");

            UserDAO result = DAOFactory.getUserDAO();

            assertThat(result, is(instanceOf(UserDAOMySQL.class)));
        }
    }

    @Test
    public void testGetUserDAOWithAnyOtherStringAsUserDAOValueInProperties() {
        try (MockedStatic<Utility> utilityMock = mockStatic(Utility.class)) {
            utilityMock.when(() -> Utility.getValueFromProperties("userDAO")).thenReturn("SomeDBMS");

            UserDAO result = DAOFactory.getUserDAO();

            assertThat(result, is(instanceOf(UserDAOMySQL.class)));
        }
    }

    @Test
    void testGetTeamDAOWithIOException() {
        try (MockedStatic<Utility> utilityMock = mockStatic(Utility.class)) {
            utilityMock.when(() -> Utility.getValueFromProperties("teamDAO")).thenThrow(IOException.class);

            TeamDAO result = DAOFactory.getTeamDAO();

            assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
        }
    }

    @Test
    void testGetUserDAOWithIOException() {
        try (MockedStatic<Utility> utilityMock = mockStatic(Utility.class)) {
            utilityMock.when(() -> Utility.getValueFromProperties("userDAO")).thenThrow(IOException.class);

            UserDAO result = DAOFactory.getUserDAO();

            assertThat(result, is(instanceOf(UserDAOMySQL.class)));
        }
    }
}
