package org.apache.tika.mime;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)
public class MediaType_hasParameters_15_3_Test {

    @Mock
    private MediaType mockMediaType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHasParametersEmpty() {
        // Arrange
        when(mockMediaType.getParameters()).thenReturn(Collections.emptyMap());
        // Act
        boolean result = mockMediaType.hasParameters();
        // Assert
        assertFalse(result);
    }

    @Test
    public void testHasParametersNonEmpty() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        when(mockMediaType.getParameters()).thenReturn(parameters);
        // Act
        boolean result = mockMediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersNull() {
        // Arrange
        when(mockMediaType.getParameters()).thenReturn(null);
        // Act
        boolean result = mockMediaType.hasParameters();
        // Assert
        assertFalse(result);
    }

    @Test
    public void testHasParametersWithMultipleParameters() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("param2", "value2");
        when(mockMediaType.getParameters()).thenReturn(parameters);
        // Act
        boolean result = mockMediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithEmptyValue() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "");
        when(mockMediaType.getParameters()).thenReturn(parameters);
        // Act
        boolean result = mockMediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithSpecialCharactersInValue() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value with special chars: @,;:\"/\\[]?\\s");
        when(mockMediaType.getParameters()).thenReturn(parameters);
        // Act
        boolean result = mockMediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithMultipleSpecialCharactersInValue() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value with multiple special chars: @,;:\"/\\[]?\\s");
        when(mockMediaType.getParameters()).thenReturn(parameters);
        // Act
        boolean result = mockMediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithMultipleSpecialCharactersInValueAndEmptyValue() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value with multiple special chars: @,;:\"/\\[]?\\s");
        parameters.put("param2", "");
        when(mockMediaType.getParameters()).thenReturn(parameters);
        // Act
        boolean result = mockMediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithMultipleSpecialCharactersInValueAndNonEmptyValue() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value with multiple special chars: @,;:\"/\\[]?\\s");
        parameters.put("param2", "value2");
        when(mockMediaType.getParameters()).thenReturn(parameters);
        // Act
        boolean result = mockMediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithMultipleSpecialCharactersInValueAndMultipleNonEmptyValues() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value with multiple special chars: @,;:\"/\\[]?\\s");
        parameters.put("param2", "value2");
        parameters.put("param3", "value3");
        when(mockMediaType.getParameters()).thenReturn(parameters);
        // Act
        boolean result = mockMediaType.hasParameters();
        // Assert
        assertTrue(result);
    }
}
