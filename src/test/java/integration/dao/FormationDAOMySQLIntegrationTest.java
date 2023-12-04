package integration.dao;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.FormationDAO;
import com.spme.fantasolver.entity.Formation;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FormationDAOMySQLIntegrationTest {

    @BeforeAll
    public static void initialize() {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));
    }

    @Test
    public void testRetrieveFormationWithFormationsInDatabase() throws SQLException {
        FormationDAO formationDAO = DAOFactory.getFormationDAO();

        Set<Formation> result = formationDAO.retrieveFormations();

        assertThat(result, is(not(empty())));
    }
}
