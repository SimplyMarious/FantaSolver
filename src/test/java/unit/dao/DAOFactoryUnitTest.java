package unit.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mockStatic;
import org.mockito.MockedStatic;

import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.dao.TeamDAO;
import com.spme.fantasolver.dao.TeamDAOMySQL;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.Test;



public class DAOFactoryUnitTest {

    @Test
    public void testGetTeamDAOWithMySQLAsTeamDAOValueInProperties() {
        try (MockedStatic<Utility> utilityMock = mockStatic(Utility.class)) {
            utilityMock.when(() -> Utility.getValueFromProperties("teamDAO")).thenReturn("MySQL");

            TeamDAO result = DAOFactory.getTeamDAO();

            assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
        }
    }

    @Test
    public void testGetTeamDAOWithAnyOtherStringAsTeamDAOValueInProperties() {
        try (MockedStatic<Utility> utilityMock = mockStatic(Utility.class)) {
            utilityMock.when(() -> Utility.getValueFromProperties("teamDAO")).thenReturn("SomeDBMS");

            TeamDAO result = DAOFactory.getTeamDAO();

            assertThat(result, is(instanceOf(TeamDAOMySQL.class)));
        }
    }
}
