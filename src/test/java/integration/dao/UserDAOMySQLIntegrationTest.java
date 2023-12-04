package integration.dao;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.UserDAO;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.spme.fantasolver.dao.MySQLConnectionManager.connectToDatabase;
import static org.junit.jupiter.api.Assertions.*;


class UserDAOMySQLIntegrationTest {

    private String username;
    private String password;
    private boolean signUpResult;

    @BeforeAll
    static void initialize() {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));
    }

    @BeforeEach
    void setUpIntegrationTest() {
        username = "testUser";
        password = "testPassword";
        signUpResult = false;
    }

    @AfterEach
    void clean() throws SQLException, ClassNotFoundException {
        if(signUpResult){
            Connection connection = connectToDatabase();
            String deleteQuery = "DELETE FROM user WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();

            connection.close();
        }
    }

    @Test
    void testSignUpWithUserNotInDatabase() {
        UserDAO userDAOMySQL = DAOFactory.getUserDAO();

        signUpResult = userDAOMySQL.signUp(username, password);

        assertTrue(signUpResult);
    }

    @Test
    void testSignUpWithUserInDatabase() {
        UserDAO userDAOMySQL = DAOFactory.getUserDAO();
        signUpResult = userDAOMySQL.signUp(username, password);

        boolean signUpSameUsernameResult = userDAOMySQL.signUp(username, password);

        assertFalse(signUpSameUsernameResult);
    }

    @Test
    void testSignInWithUserInDatabase() {
        UserDAO userDAOMySQL = DAOFactory.getUserDAO();
        signUpResult = userDAOMySQL.signUp(username, password);

        boolean signInResult = userDAOMySQL.signIn(username, password);

        assertTrue(signInResult);
    }

    @Test
    void testSignInWithUserNotInDatabase() {
        UserDAO userDAOMySQL = DAOFactory.getUserDAO();

        boolean result = userDAOMySQL.signIn("", "");

        assertFalse(result);
    }

}
