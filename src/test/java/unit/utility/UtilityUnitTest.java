package unit.utility;
import com.spme.fantasolver.utility.Utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;


class UtilityUnitTest {
    @Mock
    private Properties mockProperties;

    @Mock
    private FileInputStream mockFileInputStream;

    AutoCloseable open;

    @BeforeEach
    void setUp() {
        open = openMocks(this);
        Utility.setPropertiesReadingTools(mockProperties, mockFileInputStream);
    }

    @AfterEach
    void clean() throws Exception {
        open.close();
    }

    @Test
    void testGetValueFromPropertiesWithRightKeyAndValue() throws IOException {

        String key = "testKey";
        String expectedValue = "testValue";

        doNothing().when(mockProperties).load(mockFileInputStream);
        when(mockProperties.getProperty(key)).thenReturn(expectedValue);

        assertThat(Utility.getValueFromProperties(key), is(expectedValue));

        verify(mockProperties, times(1)).load(mockFileInputStream);
        verify(mockProperties, times(1)).getProperty(key);

    }


    @Test
    void testCheckStringValidityWithValidStringAndValidBoundaries() {
        String string = "ValidString";
        int minLength = 5;
        int maxLength = 15;

        assertTrue(Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    void testCheckStringValidityWithTooShortStringAndValidBoundaries() {
        String string = "test";
        int minLength = 5;
        int maxLength = 15;

        assertFalse(Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    void testCheckStringValidityWithTooLongStringAndValidBoundaries() {
        String string = "testStringTooLong";
        int minLength = 5;
        int maxLength = 10;

        assertFalse(Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    void testCheckStringValidityWithNullStringAndValidBoundaries() {
        String string = null;
        int minLength = 5;
        int maxLength = 15;

        assertThrows(IllegalArgumentException.class, () -> Utility.checkStringValidity(string, minLength, maxLength));
    }

    @ParameterizedTest (name = "Text {index} ==> validity with: string = {0}, minLength = {1}, maxLength = {2})")
    @MethodSource("addInputProvider")
    void testCheckStringValidity(String string, int minLength, int maxLength) {
        assertThrows(IllegalArgumentException.class, () -> Utility.checkStringValidity(string, minLength, maxLength));
    }

    static Stream<Arguments> addInputProvider() {
        return Stream.of(
                Arguments.of(null, 5, 1),
                Arguments.of("ValidString", 10, 5),
                Arguments.of(null, 5, 15)
        );
    }

    @Test
    void testCheckStringValidityWithNegativeMin() {
        String string = "ValidString";
        int minLength = -3;
        int maxLength = 5;

        assertThrows(IllegalArgumentException.class, () -> Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    void testCheckStringValidityWithNegativeMax() {
        String string = "ValidString";
        int minLength = 3;
        int maxLength = -10;

        assertThrows(IllegalArgumentException.class, () -> Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    void testCheckStringValidityWithNegativeBoundaries() {
        String string = "ValidString";
        int minLength = -3;
        int maxLength = 5;

        assertThrows(IllegalArgumentException.class, () -> Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    void testCheckStringValidityWithEqualeBoundaries() {
        String string = "ValidStrin";
        int minLength = 10;
        int maxLength = 10;

        assertTrue(Utility.checkStringValidity(string, minLength, maxLength));
    }

    @Test
    void testAreStringsDifferentFromEachOtherWithAllDifferentStrings() {
        List<String> strings = List.of("one", "two", "three");
        assertTrue(Utility.areStringsDifferentFromEachOther(strings));
    }

    @Test
    void testAreStringsDifferentFromEachOtherWithAllEqualStrings() {
        List<String> strings = List.of("one", "one", "one");
        assertFalse(Utility.areStringsDifferentFromEachOther(strings));
    }

    @Test
    void testAreStringsDifferentFromEachOtherWithEqualStringPair() {
        List<String> strings = List.of("one", "two", "one");
        assertFalse(Utility.areStringsDifferentFromEachOther(strings));
    }
    @Test
    void testAreStringsDifferentFromEachOtherWithNullList() {
        assertThrows(IllegalArgumentException.class, () -> Utility.areStringsDifferentFromEachOther(null));
    }

    @Test
    void testAreStringsDifferentFromEachOtherWithEmptyList() {
        List<String> strings = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> Utility.areStringsDifferentFromEachOther(strings));
    }

    @Test
    void testAreStringsDifferentFromEachOtherWithOneString() {
        List<String> strings = List.of("one");
        assertThrows(IllegalArgumentException.class, () -> Utility.areStringsDifferentFromEachOther(strings));
    }

    @Test
    void testGetFormattedStringsWithNullList() {
        assertThrows(IllegalArgumentException.class, () -> Utility.getFormattedStrings(null));
    }

    @Test
    void testGetFormattedStringsWithEmptyList() {
        List<String> strings = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> Utility.getFormattedStrings(strings));

    }

    @Test
    void testGetFormattedStringsWithSingleString() {
        List<String> strings = List.of("One");
        String result = Utility.getFormattedStrings(strings);
        assertThat(result, is("One"));
    }

    @Test
    void testGetFormattedStringsWithMultipleStrings() {
        List<String> strings = List.of("One", "Two", "Three");
        String result = Utility.getFormattedStrings(strings);
        assertThat(result, is("One, Two, Three"));
    }

    @Test
    void testGetFormattedStringsWithWhitespace() {
        List<String> strings = List.of("  One  ", "Two", "   Three   ");
        String result = Utility.getFormattedStrings(strings);
        assertThat(result, is("  One  , Two,    Three   "));
    }
}
