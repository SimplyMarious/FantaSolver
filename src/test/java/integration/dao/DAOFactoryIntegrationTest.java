package integration.dao;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.dao.*;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

class DAOFactoryIntegrationTest {
    @BeforeAll
    static void initialize() {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));
    }

    @Test
    void testGetTeamDAOWithMySQLAsTeamDAOValueInProperties() {
        TeamDAO result = DAOFactory.getTeamDAO();

        assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
    }

    @Test
    void testGetTeamDAOWithAnyOtherStringAsTeamDAOValueInProperties() {
        TeamDAO result = DAOFactory.getTeamDAO();

        assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
        assertSame(TeamDAOMySQL.class, DAOFactory.getTeamDAO().getClass());
    }

    @Test
    void testGetUserDAOWithMySQLAsUserDAOValueInProperties() {
        UserDAO result = DAOFactory.getUserDAO();

        assertThat(result, is(instanceOf(UserDAOMySQL.class)));
    }

    @Test
    void testGetUserDAOWithAnyOtherStringAsUserDAOValueInProperties() {
        UserDAO result = DAOFactory.getUserDAO();

        assertThat(result, is(instanceOf(UserDAOMySQL.class)));
        assertSame(UserDAOMySQL.class, DAOFactory.getUserDAO().getClass());
    }

    @Test
    void testGetFormationDAOWithMySQLAsUserDAOValueInProperties() {
        FormationDAO result = DAOFactory.getFormationDAO();

        assertThat(result, is(instanceOf(FormationDAOMySQL.class)));
    }

    @Test
    void testGetFormationDAOWithAnyOtherStringAsUserDAOValueInProperties() {
        FormationDAO result = DAOFactory.getFormationDAO();

        assertThat(result, is(instanceOf(FormationDAOMySQL.class)));
        assertSame(FormationDAOMySQL.class, DAOFactory.getFormationDAO().getClass());
    }

}
