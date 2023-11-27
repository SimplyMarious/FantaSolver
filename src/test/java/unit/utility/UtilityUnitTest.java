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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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


    @Test
    public void testCheckStringValidityWithValidStringAndValidBoundaries() {
        String string = "ValidString";
        int minLength = 5;
        int maxLength = 15;

        assertTrue(Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    public void testCheckStringValidityWithTooShortStringAndValidBoundaries() {
        String string = "test";
        int minLength = 5;
        int maxLength = 15;

        assertFalse(Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    public void testCheckStringValidityWithTooLongStringAndValidBoundaries() {
        String string = "testStringTooLong";
        int minLength = 5;
        int maxLength = 10;

        assertFalse(Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    public void testCheckStringValidityWithNullStringAndValidBoundaries() {
        String string = null;
        int minLength = 5;
        int maxLength = 15;

        assertThrows(IllegalArgumentException.class, () -> Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    public void testCheckStringValidityWithNullStringAndInvalidBoundaries() {
        String string = null;
        int minLength = 5;
        int maxLength = 1;

        assertThrows(IllegalArgumentException.class, () -> Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    public void testCheckStringValidityWithValidStringAndMinHigherThanMax() {
        String string = "ValidString";
        int minLength = 10;
        int maxLength = 5;

        assertThrows(IllegalArgumentException.class, () -> Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    public void testCheckStringValidityWithNegativeMin() {
        String string = "ValidString";
        int minLength = -3;
        int maxLength = 5;

        assertThrows(IllegalArgumentException.class, () -> Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    public void testCheckStringValidityWithNegativeMax() {
        String string = "ValidString";
        int minLength = 3;
        int maxLength = -10;

        assertThrows(IllegalArgumentException.class, () -> Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    public void testCheckStringValidityWithNegativeBoundaries() {
        String string = "ValidString";
        int minLength = -3;
        int maxLength = 5;

        assertThrows(IllegalArgumentException.class, () -> Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    public void testCheckStringValidityWithEqualeBoundaries() {
        String string = "ValidStrin";
        int minLength = 10;
        int maxLength = 10;

        assertTrue(Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    public void testAreStringsDifferentFromEachOtherWithAllDifferentStrings() {
        List<String> strings = List.of("one", "two", "three");
        assertTrue(Utility.areStringsDifferentFromEachOther(strings));
    }

    @Test
    public void testAreStringsDifferentFromEachOtherWithAllEqualStrings() {
        List<String> strings = List.of("one", "one", "one");
        assertFalse(Utility.areStringsDifferentFromEachOther(strings));
    }

    @Test
    public void testAreStringsDifferentFromEachOtherWithEqualStringPair() {
        List<String> strings = List.of("one", "two", "one");
        assertFalse(Utility.areStringsDifferentFromEachOther(strings));
    }
    @Test
    public void testAreStringsDifferentFromEachOtherWithNullList() {
        assertThrows(IllegalArgumentException.class, () -> Utility.areStringsDifferentFromEachOther(null));
    }

    @Test
    public void testAreStringsDifferentFromEachOtherWithEmptyList() {
        List<String> strings = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> Utility.areStringsDifferentFromEachOther(strings));
    }

    @Test
    public void testAreStringsDifferentFromEachOtherWithOneString() {
        List<String> strings = List.of("one");
        assertThrows(IllegalArgumentException.class, () -> Utility.areStringsDifferentFromEachOther(strings));
    }

}
