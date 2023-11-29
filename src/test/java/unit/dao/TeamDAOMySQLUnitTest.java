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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TeamDAOMySQLUnitTest {
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

    private TeamDAOMySQL teamDAOMySQL;

    @BeforeEach
    public void setUp(){
        mockTeam = mock(Team.class);
        mockUser = mock(User.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        teamDAOMySQL = new TeamDAOMySQL();
        mockMySQLConnectionManager = mockStatic(MySQLConnectionManager.class);
        mockResultSet = mock(ResultSet.class);
        teamDAOMySQL = new TeamDAOMySQL();
    }

    @AfterEach
    public void clean() {
        try {
            mockMySQLConnectionManager.close();
            mockConnection.close();
            mockPreparedStatement.close();
            mockResultSet.close();
        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateTeamWithUndoneTeamDeletion() throws SQLException {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Change as needed
        boolean result = teamDAOMySQL.updateTeam(mockTeam, mockUser);

        assertFalse(result);

    }

    @Test
    public void testUpdateTeamWithDoneTeamDeletionUndoneNewTeamInsertion() throws SQLException {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1, 0); // Change as needed
        boolean result = teamDAOMySQL.updateTeam(mockTeam, mockUser);

        assertFalse(result);

    }

    @Test
    public void testUpdateTeamWithDoneTeamDeletionDoneNewTeamInsertionUndonePlayersInTeamInsertion() throws SQLException {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        Team team = new Team("TestTeam", Set.of(new Player("Player1"), new Player("Player2")));
        when(mockPreparedStatement.executeUpdate()).thenReturn(1, 1, 1, 0);
        boolean result = teamDAOMySQL.updateTeam(team, mockUser);

        assertFalse(result);

    }

    @Test
    public void testUpdateTeamWithDoneTeamDeletionDoneNewTeamInsertionDonePlayersInTeamInsertion() throws SQLException {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        Team team = new Team("TestTeam", Set.of(new Player("Player1"), new Player("Player2")));
        when(mockPreparedStatement.executeUpdate()).thenReturn(1, 1, 1, 1);
        boolean result = teamDAOMySQL.updateTeam(team, mockUser);

        assertTrue(result);
    }

    @Test
    public void testUpdateTeamWithClassNotFoundExceptionThrown() {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenThrow(ClassNotFoundException.class);

        assertFalse(teamDAOMySQL.updateTeam(mockTeam, mockUser));
    }

    @Test
    public void testUpdateTeamWithSQLExceptionThrown() {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenThrow(SQLException.class);

        assertFalse(teamDAOMySQL.updateTeam(mockTeam, mockUser));
    }

    @Test
    public void testRetrieveTeamWithTeamNotInDatabase() throws SQLException {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        try {
            Team result = teamDAOMySQL.retrieveTeam(mockUser);
            assertNull(result);
        } catch (InternalException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

    }

    @Test
    public void testRetrieveTeamWithTeamInDatabase() throws SQLException {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);

        try {
            Team result = teamDAOMySQL.retrieveTeam(mockUser);
            assertNotNull(result);
        } catch (InternalException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveTeamWithConnectionException() {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenThrow(SQLException.class);
        assertThrows(InternalException.class, ()->teamDAOMySQL.retrieveTeam(mockUser));
    }

    @Test
    public void testRetrieveTeamWithPreparedStatementException() throws SQLException {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());

        assertThrows(InternalException.class, ()->teamDAOMySQL.retrieveTeam(mockUser));
    }

    @Test
    public void testRetrieveTeamWithExecuteQueryException() throws SQLException {
        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException());

        assertThrows(InternalException.class, ()->teamDAOMySQL.retrieveTeam(mockUser));
    }
}
