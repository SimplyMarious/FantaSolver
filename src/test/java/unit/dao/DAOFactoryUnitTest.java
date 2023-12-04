package unit.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import com.spme.fantasolver.dao.*;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class DAOFactoryUnitTest {

    @Mock
    MockedStatic<Utility> mockUtility;
    @Mock
    MockedStatic<Logger> mockStaticLogger;
    @Mock
    Logger mockLogger;

    @BeforeEach
    void setUp() {
        mockUtility = mockStatic(Utility.class);
        mockStaticLogger = mockStatic(Logger.class);
        mockLogger = mock(Logger.class);

        mockStaticLogger.when(() -> Logger.getLogger("DAOFactory")).thenReturn(mockLogger);
        doNothing().when(mockLogger).info(any(String.class));
    }

    @AfterEach
    void clean(){
        mockUtility.close();
        mockStaticLogger.close();
        DAOFactory.resetFactory();
    }

    @Test
    void testGetTeamDAOWithMySQLAsTeamDAOValueInProperties() {
        mockUtility.when(() -> Utility.getValueFromProperties("teamDAO")).thenReturn("MySQL");

        TeamDAO result = DAOFactory.getTeamDAO();

        assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
    }

    @Test
    void testGetTeamDAOWithAnyOtherStringAsTeamDAOValueInProperties() {
        mockUtility.when(() -> Utility.getValueFromProperties("teamDAO")).thenReturn("SomeDBMS");

        TeamDAO result = DAOFactory.getTeamDAO();

        assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
        assertSame(TeamDAOMySQL.class, DAOFactory.getTeamDAO().getClass());
    }

    @Test
    void testGetUserDAOWithMySQLAsUserDAOValueInProperties() {
        mockUtility.when(() -> Utility.getValueFromProperties("userDAO")).thenReturn("MySQL");

        UserDAO result = DAOFactory.getUserDAO();

        assertThat(result, is(instanceOf(UserDAOMySQL.class)));
    }

    @Test
    void testGetUserDAOWithAnyOtherStringAsUserDAOValueInProperties() {
        mockUtility.when(() -> Utility.getValueFromProperties("userDAO")).thenReturn("SomeDBMS");

        UserDAO result = DAOFactory.getUserDAO();

        assertThat(result, is(instanceOf(UserDAOMySQL.class)));
        assertSame(UserDAOMySQL.class, DAOFactory.getUserDAO().getClass());
    }

    @Test
    void testGetTeamDAOWithIOException() {
        mockUtility.when(() -> Utility.getValueFromProperties(eq("teamDAO"))).thenThrow(new IOException("Simulated IOException"));

        TeamDAO result = DAOFactory.getTeamDAO();

        assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
        verify(mockLogger).info("Error getting the DBMS value for TeamDAO: Simulated IOException");
    }

    @Test
    void testGetUserDAOWithIOException() {
        mockUtility.when(() -> Utility.getValueFromProperties(eq("userDAO"))).thenThrow(new IOException("Simulated IOException"));

        UserDAO result = DAOFactory.getUserDAO();

        assertThat(result, is(instanceOf(UserDAOMySQL.class)));
        verify(mockLogger).info("Error getting the DBMS value for UserDAO: Simulated IOException");
    }

    @Test
    void testGetFormationDAOWithMySQLAsUserDAOValueInProperties() {
        mockUtility.when(() -> Utility.getValueFromProperties("formationDAO")).thenReturn("MySQL");

        FormationDAO result = DAOFactory.getFormationDAO();

        assertThat(result, is(instanceOf(FormationDAOMySQL.class)));
    }

    @Test
    void testGetFormationDAOWithAnyOtherStringAsUserDAOValueInProperties() {
        mockUtility.when(() -> Utility.getValueFromProperties("formationDAO")).thenReturn("SomeDBMS");

        FormationDAO result = DAOFactory.getFormationDAO();

        assertThat(result, is(instanceOf(FormationDAOMySQL.class)));
        assertSame(FormationDAOMySQL.class, DAOFactory.getFormationDAO().getClass());
    }

    @Test
    void testGetFormationDAOWithIOException() {
        mockUtility.when(() -> Utility.getValueFromProperties(eq("formationDAO"))).thenThrow(new IOException("Simulated IOException"));

        FormationDAO result = DAOFactory.getFormationDAO();

        assertThat(result, is(instanceOf(FormationDAOMySQL.class)));
        verify(mockLogger).info("Error getting the DBMS value for FormationDAO: Simulated IOException");
    }

}
