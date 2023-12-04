package unit.dao;

import com.spme.fantasolver.dao.MySQLConnectionManager;
import com.spme.fantasolver.utility.Notifier;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MySQLConnectionManagerUnitTest {
    @Mock
    MockedStatic<Utility> mockUtility;
    @Mock
    MockedStatic<Notifier> mockNotifier;
    @Mock
    MockedStatic<Logger> mockStaticLogger;
    @Mock
    MockedStatic<DriverManager> mockDriverManager;
    @Mock
    Logger mockLogger;

    @BeforeEach
    void setUp() {
        mockUtility = mockStatic(Utility.class);
        mockStaticLogger = mockStatic(Logger.class);
        mockDriverManager = mockStatic(DriverManager.class);
        mockNotifier = mockStatic(Notifier.class);
        mockLogger = mock(Logger.class);

        mockStaticLogger.when(() -> Logger.getLogger("MySQLConnectionManager")).thenReturn(mockLogger);
        doNothing().when(mockLogger).info(any(String.class));
    }

    @AfterEach
    void clean(){
        mockUtility.close();
        mockNotifier.close();
        mockStaticLogger.close();
        mockDriverManager.close();
    }

    @Test
    void testConnectToDatabaseSuccess() throws ClassNotFoundException, SQLException {
        Connection mockConnection = mock(Connection.class);
        mockDriverManager.when(()->DriverManager.getConnection(anyString(), anyString(),anyString()))
                .thenReturn(mockConnection);

        Connection result = MySQLConnectionManager.connectToDatabase();

        assertNotNull(result);
        verify(mockLogger, never()).info(anyString());
    }

    @Test
    void testConnectToDatabaseWithIOException() throws SQLException, ClassNotFoundException {
        mockUtility.when(() -> Utility.getValueFromProperties(anyString())).thenThrow(new IOException("Simulated IOException"));

        Connection connection = MySQLConnectionManager.connectToDatabase();

        assertNull(connection);
        verify(mockLogger).info("Error while connection: Simulated IOException");
    }
}
