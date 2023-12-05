package integration.dao;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static com.spme.fantasolver.dao.MySQLConnectionManager.connectToDatabase;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MySQLConnectionManagerIntegrationTest {

    @BeforeAll
    static void initialize() {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));
    }

    @Test
    void testConnectionToDatabase() throws SQLException, ClassNotFoundException {
        Connection connection = connectToDatabase();
        assertNotNull(connection);
        connection.close();
    }
}
