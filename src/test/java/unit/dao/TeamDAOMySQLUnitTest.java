package unit.dao;

import com.spme.fantasolver.dao.InternalException;
import com.spme.fantasolver.dao.MySQLConnectionManager;
import com.spme.fantasolver.dao.TeamDAOMySQL;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.entity.User;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class TeamDAOMySQLUnitTest {
    @Mock
    private Team mockTeam;
    @Mock
    private User mockUser;
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

    private TeamDAOMySQL teamDAOMySQL;

    @BeforeEach
    void setUp(){
        mockTeam = mock(Team.class);
        mockUser = mock(User.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockMySQLConnectionManager = mockStatic(MySQLConnectionManager.class);
        mockResultSet = mock(ResultSet.class);
        mockStaticLogger = mockStatic(Logger.class);
        mockLogger = mock(Logger.class);
        teamDAOMySQL = new TeamDAOMySQL();

        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockConnection);

        mockStaticLogger.when(() -> Logger.getLogger("TeamDAOMySQL")).thenReturn(mockLogger);
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
    void testUpdateTeamWithUndoneTeamDeletion() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Change as needed
        boolean result = teamDAOMySQL.updateTeam(mockTeam, mockUser);

        assertFalse(result);

    }

    @Test
    void testUpdateTeamWithMoreThanOneTeamDeletion() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeUpdate()).thenReturn(2); // Change as needed
        boolean result = teamDAOMySQL.updateTeam(mockTeam, mockUser);

        assertFalse(result);

    }

    @Test
    void testUpdateTeamWithDoneTeamDeletionUndoneNewTeamInsertion() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1, 0); // Change as needed
        boolean result = teamDAOMySQL.updateTeam(mockTeam, mockUser);

        assertFalse(result);

    }

    @Test
    void testUpdateTeamWithDoneTeamDeletionDoneNewTeamInsertionUndonePlayersInTeamInsertion() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        Team team = new Team("TestTeam", Set.of(new Player("Player1"), new Player("Player2")));
        when(mockPreparedStatement.executeUpdate()).thenReturn(1, 1, 1, 0);
        boolean result = teamDAOMySQL.updateTeam(team, mockUser);

        assertFalse(result);

    }

    @Test
    void testUpdateTeamWithDoneTeamDeletionDoneNewTeamInsertionDonePlayersInTeamInsertion() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        Team team = new Team("TestTeam", Set.of(new Player("Player1"), new Player("Player2")));
        when(mockPreparedStatement.executeUpdate()).thenReturn(1, 1, 1, 1);
        boolean result = teamDAOMySQL.updateTeam(team, mockUser);

        assertTrue(result);
    }

    @Test
    void testUpdateTeamWithClassNotFoundExceptionThrown() {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenThrow(ClassNotFoundException.class);

        assertFalse(teamDAOMySQL.updateTeam(mockTeam, mockUser));
    }

    @Test
    void testUpdateTeamWithSQLExceptionThrown() {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenThrow(SQLException.class);

        assertFalse(teamDAOMySQL.updateTeam(mockTeam, mockUser));
    }

    @Test
    void testRetrieveTeamWithTeamNotInDatabase() throws SQLException, InternalException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Team result = teamDAOMySQL.retrieveTeam(mockUser);
        assertNull(result);
    }

    @Test
    void testRetrieveTeamWithTeamInDatabase() throws SQLException, InternalException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);

        Team result = teamDAOMySQL.retrieveTeam(mockUser);
        assertNotNull(result);
    }

    @Test
    void testRetrieveTeamWithConnectionSQLException() {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenThrow(SQLException.class);

        assertThrows(InternalException.class, ()->teamDAOMySQL.retrieveTeam(mockUser));
    }

    @Test
    void testRetrieveTeamWithConnectionClassNotFoundException() {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenThrow(ClassNotFoundException.class);

        assertThrows(InternalException.class, ()->teamDAOMySQL.retrieveTeam(mockUser));
    }

    @Test
    void testRetrieveTeamWithPreparedStatementException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());

        assertThrows(InternalException.class, ()->teamDAOMySQL.retrieveTeam(mockUser));
    }

    @Test
    void testRetrieveTeamWithExecuteQueryException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException());

        assertThrows(InternalException.class, ()->teamDAOMySQL.retrieveTeam(mockUser));
    }
}
