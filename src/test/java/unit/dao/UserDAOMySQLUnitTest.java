package unit.dao;

import com.spme.fantasolver.dao.DataRetriever;
import com.spme.fantasolver.dao.UserDAOMySQL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOMySQLUnitTest {

    private String username;
    private String password;
    private MockedStatic<DataRetriever> dataRetrieverMock;
    private Connection mockedConnection;
    private PreparedStatement mockedPreparedStatement;
    private ResultSet mockedResultSet;
    private UserDAOMySQL userDAOMySQL;

    @BeforeEach
    public void setUp() {
        username = "testUser";
        password = "testPassword";
        dataRetrieverMock = mockStatic(DataRetriever.class);
        mockedConnection = mock(Connection.class);
        mockedPreparedStatement = mock(PreparedStatement.class);
        mockedResultSet = mock(ResultSet.class);
        userDAOMySQL = new UserDAOMySQL();
    }

    @AfterEach
    public void clean() {
        try {
            dataRetrieverMock.close();
            mockedConnection.close();
            mockedPreparedStatement.close();
            mockedResultSet.close();
        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSignUpWithUserNotInDatabase() {
        try {
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);

            boolean result = userDAOMySQL.signUp(username, password);

            assertTrue(result);
            verify(mockedConnection, times(2)).close();
            verify(mockedPreparedStatement, times(1)).executeUpdate();
            verify(mockedPreparedStatement, times(1)).executeQuery();

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSignUpWithUserInDatabase() {
        try {
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mock(Connection.class));
            UserDAOMySQL mockedUserDAOMySQL = spy(new UserDAOMySQL());
            doReturn(true).when(mockedUserDAOMySQL).isUserExist(username);

            boolean result = mockedUserDAOMySQL.signUp(username, password);

            assertFalse(result);

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSignUpWithPreparedStatementException() {
        try {
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedConnection.prepareStatement(anyString())).thenThrow(new SQLException());

            boolean result = userDAOMySQL.signUp(username, password);

            assertFalse(result);

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSignUpWithExecuteUpdateException() {
        try {
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
            when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            when(mockedPreparedStatement.executeUpdate()).thenThrow(new SQLException());

            boolean result = userDAOMySQL.signUp(username, password);

            assertFalse(result);

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSignUpWithSetStringException() {
        try {
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            doThrow(new SQLException()).when(mockedPreparedStatement).setString(eq(1), anyString());

            boolean result = userDAOMySQL.signUp(username, password);

            assertFalse(result);

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSignInWithUserInDatabase() {
        try {
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
            when(mockedResultSet.next()).thenReturn(true);

            boolean result = userDAOMySQL.signIn(username, password);

            assertTrue(result);
            verify(mockedConnection, times(1)).close();
            verify(mockedPreparedStatement, times(1)).executeQuery();

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSignInWithUserNotInDatabase() {
        try {
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
            when(mockedResultSet.next()).thenReturn(false);

            boolean result = userDAOMySQL.signIn(username, password);

            assertFalse(result);
            verify(mockedConnection, times(1)).close();
            verify(mockedPreparedStatement, times(1)).executeQuery();

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSignInWithPreparedStatementException() {
        try {
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedConnection.prepareStatement(anyString())).thenThrow(new SQLException());

            boolean result = userDAOMySQL.signIn(username, password);

            assertFalse(result);

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSignInWithExecuteUpdateException() {
        try {
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
            when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            when(mockedPreparedStatement.executeUpdate()).thenThrow(new SQLException());

            boolean result = userDAOMySQL.signIn(username, password);

            assertFalse(result);

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSignInWithSetStringException() {
        try {
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            doThrow(new SQLException()).when(mockedPreparedStatement).setString(eq(1), anyString());

            boolean result = userDAOMySQL.signIn(username, password);

            assertFalse(result);

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
