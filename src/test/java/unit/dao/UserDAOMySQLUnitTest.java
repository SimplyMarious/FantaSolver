package unit.dao;

import com.spme.fantasolver.dao.MySQLConnectionManager;
import com.spme.fantasolver.dao.UserDAOMySQL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOMySQLUnitTest {

    @Mock
    private MockedStatic<MySQLConnectionManager> mockMySQLConnectionManager;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    @Mock
    MockedStatic<Logger> mockStaticLogger;
    @Mock
    Logger mockLogger;


    private UserDAOMySQL userDAOMySQL;
    private String username;
    private String password;

    @BeforeEach
    void setUp() throws SQLException {
        username = "testUser";
        password = "testPassword";
        mockMySQLConnectionManager = mockStatic(MySQLConnectionManager.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        mockStaticLogger = mockStatic(Logger.class);
        mockLogger = mock(Logger.class);
        userDAOMySQL = new UserDAOMySQL();

        mockMySQLConnectionManager.when(MySQLConnectionManager::connectToDatabase).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        mockStaticLogger.when(() -> Logger.getLogger("UserDAOMySQL")).thenReturn(mockLogger);
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
    void testSignUpWithUserNotInDatabase() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        boolean result = userDAOMySQL.signUp(username, password);

        assertTrue(result);
        verify(mockConnection, times(2)).close();
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    void testSignUpWithUserInDatabase() throws SQLException, ClassNotFoundException {
        UserDAOMySQL mockedUserDAOMySQL = spy(new UserDAOMySQL());
        doReturn(true).when(mockedUserDAOMySQL).isUserExist(username);

        boolean result = mockedUserDAOMySQL.signUp(username, password);

        assertFalse(result);
    }

    @Test
    void testSignUpWithPreparedStatementException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());

        boolean result = userDAOMySQL.signUp(username, password);

        assertFalse(result);
    }

    @Test
    void testSignUpWithExecuteUpdateException() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException());

        boolean result = userDAOMySQL.signUp(username, password);

        assertFalse(result);
    }

    @Test
    void testSignUpWithSetStringException() throws SQLException {
        doThrow(new SQLException()).when(mockPreparedStatement).setString(eq(1), anyString());

        boolean result = userDAOMySQL.signUp(username, password);

        assertFalse(result);
    }

    @Test
    void testSignInWithUserInDatabase() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        boolean result = userDAOMySQL.signIn(username, password);

        assertTrue(result);
        verify(mockConnection, times(1)).close();
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    void testSignInWithUserNotInDatabase() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        boolean result = userDAOMySQL.signIn(username, password);

        assertFalse(result);
        verify(mockConnection, times(1)).close();
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    void testSignInWithPreparedStatementException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());

        boolean result = userDAOMySQL.signIn(username, password);

        assertFalse(result);
    }

    @Test
    void testSignInWithExecuteUpdateException() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException());

        boolean result = userDAOMySQL.signIn(username, password);

        assertFalse(result);
    }

    @Test
    void testSignInWithSetStringException() throws SQLException {
        doThrow(new SQLException()).when(mockPreparedStatement).setString(eq(1), anyString());

        boolean result = userDAOMySQL.signIn(username, password);

        assertFalse(result);
    }
}
