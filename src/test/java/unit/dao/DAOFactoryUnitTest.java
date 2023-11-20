package unit.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.TeamDAO;
import com.spme.fantasolver.dao.TeamDAOMySQL;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class DAOFactoryUnitTest {

    @Test
    public void testGetTeamDAOWithMySQLAsTeamDAOValueInProperties() {
        try (MockedStatic<Utility> utilityMock = Mockito.mockStatic(Utility.class)) {
            utilityMock.when(() -> Utility.getValueFromProperties("teamDAO")).thenReturn("MySQL");

            TeamDAO result = DAOFactory.getTeamDAO();

            assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
        }
    }

    @Test
    public void testGetTeamDAOWithAnyOtherStringAsTeamDAOValueInProperties() {
        try (MockedStatic<Utility> utilityMock = Mockito.mockStatic(Utility.class)) {
            utilityMock.when(() -> Utility.getValueFromProperties("teamDAO")).thenReturn("SomeDBMS");

            TeamDAO result = DAOFactory.getTeamDAO();

            assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
        }
    }
}
