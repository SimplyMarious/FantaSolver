package integration.dao;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.dao.*;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.entity.User;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import static com.spme.fantasolver.dao.MySQLConnectionManager.connectToDatabase;
import static org.junit.jupiter.api.Assertions.*;

public class TeamDAOMySQLIntegrationTest {
    private static TeamDAO teamDAOMySQL;
    private static User testUser;
    private static Team testTeam;

    @BeforeAll
    static void initialize() {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));

        Player[] players = new Player[11];
        Role[] roles = new Role[]{
                Role.POR, Role.DD, Role.DC, Role.DC, Role.DS,
                Role.M, Role.C, Role.M, Role.C, Role.A, Role.PC
        };

        for (int i = 0; i < 11; i++) {
            players[i] = new Player();
            players[i].setName("Player"+i);
            players[i].setRoles(Set.of(roles[i]));
        }

        testTeam = new Team("TestTeam", Set.of(players));
        testUser = new User("TestUser");
        teamDAOMySQL = DAOFactory.getTeamDAO();

    }

    private void removeUserAndTeam() throws SQLException, ClassNotFoundException {
        Connection connection = connectToDatabase();
        String deleteUser = "DELETE FROM user WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteUser);
        preparedStatement.setString(1, testUser.getUsername());
        preparedStatement.executeUpdate();

        String deleteTeam = "DELETE FROM team WHERE user_name = ?";
        preparedStatement = connection.prepareStatement(deleteTeam);
        preparedStatement.setString(1, testUser.getUsername());
        preparedStatement.executeUpdate();

        connection.close();
    }

    @Test
    void testUpdateTeam() throws SQLException, ClassNotFoundException {
        UserDAO userDAOMySQL = DAOFactory.getUserDAO();
        userDAOMySQL.signUp(testUser.getUsername(), "TestPassword");

        boolean result = teamDAOMySQL.updateTeam(testTeam, testUser);
        removeUserAndTeam();

        assertTrue(result);
    }

    @Test
    void testRetrieveTeamWithTeamInDatabase() throws InternalException, SQLException, ClassNotFoundException {
        UserDAO userDAOMySQL = DAOFactory.getUserDAO();
        userDAOMySQL.signUp(testUser.getUsername(), "TestPassword");
        teamDAOMySQL.updateTeam(testTeam, testUser);

        Team retrievedTeam = teamDAOMySQL.retrieveTeam(testUser);
        removeUserAndTeam();

        assertNotNull(retrievedTeam);
    }

    @Test
    void testRetrieveTeamWithTeamNotInDatabase() throws InternalException, SQLException, ClassNotFoundException {
        UserDAO userDAOMySQL = DAOFactory.getUserDAO();
        userDAOMySQL.signUp(testUser.getUsername(), "TestPassword");

        Team retrievedTeam = teamDAOMySQL.retrieveTeam(testUser);
        removeUserAndTeam();

        assertNull(retrievedTeam);
    }
}
