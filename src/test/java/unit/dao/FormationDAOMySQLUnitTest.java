package unit.dao;

import com.spme.fantasolver.dao.*;
import com.spme.fantasolver.entity.Formation;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.RoleException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Logger;

import static java.util.Collections.emptySet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FormationDAOMySQLUnitTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private MockedStatic<MySQLConnectionManager> mockMySQLConnectionManager;
    @Mock
    private ResultSet mockResultSet;
    @Mock
    MockedStatic<Logger> mockStaticLogger;
    @Mock
    Logger mockLogger;
    private FormationDAOMySQL formationDAOMySQL;

    @BeforeEach
    void setUp(){

        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockMySQLConnectionManager = mockStatic(MySQLConnectionManager.class);
        mockResultSet = mock(ResultSet.class);
        mockStaticLogger = mockStatic(Logger.class);
        mockLogger = mock(Logger.class);
        formationDAOMySQL = new FormationDAOMySQL();

        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockConnection);

        mockStaticLogger.when(() -> Logger.getLogger("FormationDAOMySQL")).thenReturn(mockLogger);
        doNothing().when(mockLogger).info(any(String.class));
    }

    @AfterEach
    void clean() {
        try {
            mockMySQLConnectionManager.close();
            mockConnection.close();
            mockPreparedStatement.close();
            mockResultSet.close();
            mockStaticLogger.close();

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testRetrieveFormationWithFormationsInDatabase() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString(1)).thenReturn("dummyString");
        when(mockResultSet.getShort(2)).thenReturn((short) 5);
        when(mockResultSet.getString(3)).thenReturn(Role.PC.toString());

        Set<Formation> result = formationDAOMySQL.retrieveFormations();

        assertThat(result, is(not(empty())));
    }

    @Test
    void testRetrieveFormationWithFormationsNotInDatabase() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Set<Formation> result = formationDAOMySQL.retrieveFormations();

        assertThat(result, is(empty()));
    }

    @Test
    void testRetrieveFormationWithConnectionSQLException() {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase)
                .thenThrow(new SQLException("Simulated SQLException"));

        Set<Formation> result = formationDAOMySQL.retrieveFormations();

        assertThat(result, is(emptySet()));
        verify(mockLogger).info("Error during the retrieve formations: Simulated SQLException");
    }

    @Test
    void testRetrieveFormationWithConnectionClassNotFoundException() {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase)
                .thenThrow(new ClassNotFoundException("Simulated ClassNotFoundException"));

        Set<Formation> result = formationDAOMySQL.retrieveFormations();

        assertThat(result, is(emptySet()));
        verify(mockLogger).info("Error during the retrieve formations: Simulated ClassNotFoundException");
    }

    @Test
    void testRetrieveFormationWithExecuteQueryException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Simulated SQLException"));

        Set<Formation> result = formationDAOMySQL.retrieveFormations();

        assertThat(result, is(emptySet()));
        verify(mockLogger).info("Error during the retrieve formations: Simulated SQLException");
    }

    @Test
    void testRetrieveFormationWithPrepareStatementException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Simulated SQLException"));

        Set<Formation> result = formationDAOMySQL.retrieveFormations();

        assertThat(result, is(emptySet()));
        verify(mockLogger).info("Error during the retrieve formations: Simulated SQLException");
    }

    @Test
    void testRetrieveFormationsWithRoleException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);
        RoleException mockRoleException = mock(RoleException.class);
        doNothing().when(mockLogger).info(anyString());

        Set<Formation> result = formationDAOMySQL.retrieveFormations();

        assertThat(result, empty());
        verify(mockLogger).info("Error during the retrieve formations: " + mockRoleException.getMessage());
    }
}
