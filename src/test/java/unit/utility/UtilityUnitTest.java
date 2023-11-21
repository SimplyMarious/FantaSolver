package unit.utility;
import com.spme.fantasolver.utility.Utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class UtilityUnitTest {

    @Mock
    private Properties mockProperties;

    @Mock
    private FileInputStream mockFileInputStream;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        Utility.setPropertiesReadingTools(mockProperties, mockFileInputStream);
    }


    @Test
    public void testGetValueFromPropertiesWithRightKeyAndValue() throws IOException {
        String key = "testKey";
        String expectedValue = "testValue";

        doNothing().when(mockProperties).load(mockFileInputStream);
        when(mockProperties.getProperty(key)).thenReturn(expectedValue);

        assertThat(Utility.getValueFromProperties(key), is(expectedValue));

        verify(mockProperties, times(1)).load(mockFileInputStream);
        verify(mockProperties, times(1)).getProperty(key);
    }
}
