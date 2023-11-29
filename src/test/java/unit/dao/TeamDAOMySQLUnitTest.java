package unit.dao;

import com.spme.fantasolver.dao.*;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TeamDAOMySQLUnitTest {

    private MockedStatic<MySQLConnectionManager> mockedConnectionManager;
    private Connection mockedConnection;
    private PreparedStatement mockedPreparedStatement;
    private ResultSet mockedResultSet;
    private TeamDAOMySQL teamDAOMySQL;
    private User mockedUser;


    @BeforeEach
    public void setUp() {
        mockedConnectionManager = mockStatic(MySQLConnectionManager.class);
        mockedConnection = mock(Connection.class);
        mockedPreparedStatement = mock(PreparedStatement.class);
        mockedResultSet = mock(ResultSet.class);
        mockedUser = mock(User.class);
        teamDAOMySQL = new TeamDAOMySQL();
    }

    @AfterEach
    public void clean() {
        try {
            mockedConnectionManager.close();
            mockedConnection.close();
            mockedPreparedStatement.close();
            mockedResultSet.close();
        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveTeamWithTeamNotInDatabase() throws SQLException {
        mockedConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(false);

        try {
            Team result = teamDAOMySQL.retrieveTeam(mockedUser);
            assertNull(result);
        } catch (InternalException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

    }

    @Test
    public void testRetrieveTeamWithTeamInDatabase() throws SQLException {
        mockedConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
        when(mockedResultSet.next()).thenReturn(true, false);

        try {
            Team result = teamDAOMySQL.retrieveTeam(mockedUser);
            assertNotNull(result);
        } catch (InternalException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testRetrieveTeamWithConnectionException() {
        mockedConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenThrow(SQLException.class);
        assertThrows(InternalException.class, ()->teamDAOMySQL.retrieveTeam(mockedUser));
    }

    @Test
    public void testRetrieveTeamWithPreparedStatementException() throws SQLException {
        mockedConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenThrow(new SQLException());

        assertThrows(InternalException.class, ()->teamDAOMySQL.retrieveTeam(mockedUser));
    }

    @Test
    public void testRetrieveTeamWithExecuteQueryException() throws SQLException {
        mockedConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockedConnection);
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedPreparedStatement.executeQuery()).thenThrow(new SQLException());

        assertThrows(InternalException.class, ()->teamDAOMySQL.retrieveTeam(mockedUser));
    }

}
