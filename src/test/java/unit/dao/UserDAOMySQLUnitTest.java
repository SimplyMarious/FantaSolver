package unit.dao;

import com.spme.fantasolver.dao.DataRetriever;
import com.spme.fantasolver.dao.UserDAOMySQL;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOMySQLUnitTest {

    @Test
    public void testUserDAOMySQLUnitTestWithSignUpSuccess() {
        String username = "testUser";
        String password = "testPassword";
        try (MockedStatic<DataRetriever> dataRetrieverMock = mockStatic(DataRetriever.class)) {

            Connection mockedConnection = mock(Connection.class);
            PreparedStatement mockedPreparedStatement = mock(PreparedStatement.class);
            ResultSet mockedResultSet = mock(ResultSet.class);
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);

            UserDAOMySQL userDAOMySQL = new UserDAOMySQL();
            boolean result = userDAOMySQL.signUp(username, password);

            assertTrue(result);
            verify(mockedConnection, times(1)).close();
            verify(mockedPreparedStatement, times(1)).executeUpdate();
            verify(mockedPreparedStatement, times(1)).executeQuery();

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testUserDAOMySQLUnitTestWithSignUpFailure() {
        String username = "existingUser";
        String password = "testPassword";
        try (MockedStatic<DataRetriever> dataRetrieverMock = mockStatic(DataRetriever.class)) {

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
    public void testUserDAOMySQLUnitTestWithSignUpPreparedStatementException() {
        String username = "testUser";
        String password = "testPassword";
        try (MockedStatic<DataRetriever> dataRetrieverMock = mockStatic(DataRetriever.class)) {

            Connection mockedConnection = mock(Connection.class);
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedConnection.prepareStatement(anyString())).thenThrow(new SQLException());

            UserDAOMySQL userDAOMySQL = new UserDAOMySQL();
            boolean result = userDAOMySQL.signUp(username, password);

            assertFalse(result);

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testUserDAOMySQLUnitTestWithSignUpExecuteUpdateException() {
        String username = "testUser";
        String password = "testPassword";
        try (MockedStatic<DataRetriever> dataRetrieverMock = mockStatic(DataRetriever.class)) {

            Connection mockedConnection = mock(Connection.class);
            PreparedStatement mockedPreparedStatement = mock(PreparedStatement.class);
            ResultSet mockedResultSet = mock(ResultSet.class);
            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);
            when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            when(mockedPreparedStatement.executeUpdate()).thenThrow(new SQLException());

            UserDAOMySQL userDAOMySQL = new UserDAOMySQL();
            boolean result = userDAOMySQL.signUp(username, password);

            assertFalse(result);

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testUserDAOMySQLUnitTestWithSignUpSetStringException() {
        String username = "testUser";
        String password = "testPassword";
        try (MockedStatic<DataRetriever> dataRetrieverMock = mockStatic(DataRetriever.class)) {

            Connection mockedConnection = mock(Connection.class);
            PreparedStatement mockedPreparedStatement = mock(PreparedStatement.class);

            dataRetrieverMock.when(DataRetriever::connectToDatabase).thenReturn(mockedConnection);
            when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
            doThrow(new SQLException()).when(mockedPreparedStatement).setString(eq(1), anyString());

            UserDAOMySQL userDAOMySQL = new UserDAOMySQL();
            boolean result = userDAOMySQL.signUp(username, password);

            assertFalse(result);

        } catch (SQLException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
