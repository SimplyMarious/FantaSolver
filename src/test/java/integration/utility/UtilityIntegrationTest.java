package integration.utility;

import com.spme.fantasolver.Main;
import com.spme.fantasolver.utility.Utility;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class UtilityIntegrationTest {

    @Test
    void testGetValueFromPropertiesWithRightKeyAndValue() throws IOException {
        Utility.setPropertiesReadingTools(
                new Properties(),
                Main.class.getResourceAsStream("/config.properties"));

        String key = "testKey";
        String expectedValue = "testValue";

        assertThat(Utility.getValueFromProperties(key), is(expectedValue));
    }
}
