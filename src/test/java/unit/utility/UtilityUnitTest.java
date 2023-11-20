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
        // Arrange
        String key = "testKey";
        String expectedValue = "testValue";

        // Stubbing: When properties.load() is called, return null (you can change this based on your specific needs)
        doNothing().when(mockProperties).load(mockFileInputStream);

        // Stubbing: When properties.getProperty(key) is called, return the expected value
        when(mockProperties.getProperty(key)).thenReturn(expectedValue);

        // Assert
        assertThat(Utility.getValueFromProperties(key), is(expectedValue));

        // Verify that properties.load() is called once with the provided FileInputStream
        verify(mockProperties, times(1)).load(mockFileInputStream);

        // Verify that properties.getProperty(key) is called once with the provided key
        verify(mockProperties, times(1)).getProperty(key);
    }

//    @Test
//    public void testGetValueFromPropertiesWithError() throws IOException {
//        // Arrange
//        String key = "testKey";
//
//        // Stubbing: When properties.load() is called, throw an IOException
//        doThrow(new IOException("Test IOException")).when(mockProperties).load(mockFileInputStream);
//
//        // Act and Assert
//
//        Utility.getValueFromProperties(key);
//        // If no exception is thrown, fail the test
//        fail("Expected IOException was not thrown");
//
//            // Verify that properties.load() is called once with the provided FileInputStream
//            verify(mockProperties, times(1)).load(mockFileInputStream);
//
//            // Verify that the error message is printed to System.err
//            // Note: You may need to adjust this verification based on your specific error handling logic
//            assertThat(e.getMessage(), is("Error in reading properties file."));
//        }
//    }


}
