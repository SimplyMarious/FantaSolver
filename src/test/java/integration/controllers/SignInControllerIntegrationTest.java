package integration.controllers;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.controllers.SignInController;
import com.spme.fantasolver.controllers.SignUpController;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.UserDAO;
import com.spme.fantasolver.ui.HomeStage;
import com.spme.fantasolver.ui.SignInStage;
import com.spme.fantasolver.ui.StageFactory;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.spme.fantasolver.dao.MySQLConnectionManager.connectToDatabase;
import static org.mockito.Mockito.*;

class SignInControllerIntegrationTest {

    private SignInController signInController;
    private SignInStage mockSignInStage;

    @BeforeAll
    static void initialize() {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));
    }

    @BeforeEach
    void setup() {
        mockSignInStage = mock(SignInStage.class);
        signInController = SignInController.getInstance();
        signInController.setSignInStage(mockSignInStage);
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
    void testHandlePressedSignInButtonWithFailure() {
        String username = "InvalidUser";
        String password = "InvalidPassword";

        signInController.handlePressedSignInButton(username, password);

        verify(mockSignInStage, times(1)).showFailedSignInLabel();
    }

    @Test
    void testHandlePressedSignInButtonWithSuccess() throws SQLException, ClassNotFoundException {
        UserDAO userDAOMySQL = DAOFactory.getUserDAO();
        String username = "InvalidUser";
        String password = "InvalidPassword";
        HomeStage mockHomeStage = mock(HomeStage.class);
        StageFactory mockStageFactory = mock(StageFactory.class);
        signInController.setStageFactory(mockStageFactory);
        when(mockStageFactory.createHomeStage()).thenReturn(mockHomeStage);

        userDAOMySQL.signUp(username, password);
        signInController.handlePressedSignInButton(username, password);
        removeUser(username);

        verify(mockSignInStage, never()).showFailedSignInLabel();
    }
}
