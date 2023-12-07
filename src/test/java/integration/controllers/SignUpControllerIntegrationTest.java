package integration.controllers;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.controllers.SignUpController;
import com.spme.fantasolver.ui.*;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.spme.fantasolver.dao.MySQLConnectionManager.connectToDatabase;
import static org.mockito.Mockito.*;

class SignUpControllerIntegrationTest {
    private SignUpController signUpController;
    private SignUpStage mockSignUpStage;

    @BeforeAll
    static void initialize() {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));
    }

    @BeforeEach
    void setup() {
        mockSignUpStage = mock(SignUpStage.class);
        signUpController = SignUpController.getInstance();
        signUpController.setSignUpStage(mockSignUpStage);
    }

    private void removeUser(String username) throws ClassNotFoundException, SQLException {
        Connection connection = connectToDatabase();
        String deleteQuery = "DELETE FROM user WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setString(1, username);
        preparedStatement.executeUpdate();

        connection.close();
    }

    @Test
    void testHandlePressedSignUpButtonWithSuccessfulSignUp() throws SQLException, ClassNotFoundException {
        String username = "testUser";
        String password = "testPassword";

        signUpController.handlePressedSignUpButton(username, password);

        removeUser(username);
        verify(mockSignUpStage).showSuccessfulSignUp();
        verify(mockSignUpStage, never()).showFailedSignUp();
    }

    @Test
    void testHandlePressedSignUpButtonWithFailureSignUp() throws SQLException, ClassNotFoundException {
        String username = "testUser";
        String password = "testPassword";
        signUpController.handlePressedSignUpButton(username, password);

        signUpController.handlePressedSignUpButton(username, password);

        removeUser(username);
        verify(mockSignUpStage).showFailedSignUp();
    }

}
