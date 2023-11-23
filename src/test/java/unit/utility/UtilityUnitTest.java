package unit.utility;
import com.spme.fantasolver.utility.Utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    public void testCheckStringValidityWithValidString() {
        String validString = "abcdef";
        int minLength = 1;
        int maxLength = 10;

        boolean result = Utility.checkStringValidity(validString, minLength, maxLength);

        assertTrue(result);
    }

    @Test
    public void testCheckStringValidityWithInvalidStringLength() {
        String invalidString = "abc";
        int minLength = 5;
        int maxLength = 10;

        boolean result = Utility.checkStringValidity(invalidString, minLength, maxLength);

        assertFalse(result);
    }

    @Test
    public void testCheckStringWithStringWithExactLength() {
        String validString = "12345";
        int minLength = 5;
        int maxLength = 5;

        boolean result = Utility.checkStringValidity(validString, minLength, maxLength);

        assertTrue(result);
    }

    @Test
    public void testCheckStringWithNegativeMinLength() {
        String validString = "abc";
        int minLength = -1;
        int maxLength = 10;

        boolean result = Utility.checkStringValidity(validString, minLength, maxLength);

        assertFalse(result);
    }

    @Test
    public void testCheckStringWithMinLengthGreaterThanMaxLength() {
        String validString = "abc";
        int minLength = 5;
        int maxLength = 3;

        boolean result = Utility.checkStringValidity(validString, minLength, maxLength);

        assertFalse(result);
    }
}
