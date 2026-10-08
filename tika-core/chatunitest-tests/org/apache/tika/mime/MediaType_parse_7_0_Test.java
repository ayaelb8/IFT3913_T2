package org.apache.tika.mime;

import org.apache.tika.mime.MediaType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)
public class MediaType_parse_7_0_Test {

    @InjectMocks
    private MediaType mediaType;

    @Mock
    private Charset charset;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({ "application/octet-stream, application/octet-stream", "text/plain, text/plain", "text/html, text/html", "application/xml, application/xml", "application/zip, application/zip", "invalid, null", " , null", "application/xml; charset=utf-8, application/xml; charset=utf-8", "application/xml; charset=ISO-8859-1, application/xml; charset=ISO-8859-1", "application/xml; charset=utf-8; subtype=xml, application/xml; charset=utf-8; subtype=xml" })
    public void testParse(String input, String expected) {
        when(charset.name()).thenReturn("UTF-8");
        MediaType result = mediaType.parse(input);
        if (expected != null) {
            assertEquals(expected, result.toString());
        } else {
            assertNull(result);
        }
    }

    @Test
    public void testParseWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> mediaType.parse(null));
    }

    @Test
    public void testParseWithMultipleParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("charset", "UTF-8");
        parameters.put("subtype", "xml");
        MediaType result = mediaType.parse("application/xml; charset=utf-8; subtype=xml");
        assertEquals("application/xml; charset=utf-8; subtype=xml", result.toString());
        assertEquals("UTF-8", result.getParameters().get("charset"));
        assertEquals("xml", result.getParameters().get("subtype"));
    }

    @Test
    public void testParseWithQuotedValue() {
        MediaType result = mediaType.parse("text/plain; charset=\"UTF-8\"");
        assertEquals("text/plain; charset=\"UTF-8\"", result.toString());
        assertEquals("UTF-8", result.getParameters().get("charset"));
    }

    @Test
    public void testParseWithMultipleQuotedValues() {
        MediaType result = mediaType.parse("text/plain; charset=\"UTF-8\"; subtype=\"xml\"");
        assertEquals("text/plain; charset=\"UTF-8\"; subtype=\"xml\"", result.toString());
        assertEquals("UTF-8", result.getParameters().get("charset"));
        assertEquals("xml", result.getParameters().get("subtype"));
    }

    @Test
    public void testParseWithMultipleParametersAndQuotedValues() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("charset", "UTF-8");
        parameters.put("subtype", "xml");
        MediaType result = mediaType.parse("application/xml; charset=utf-8; subtype=xml");
        assertEquals("application/xml; charset=utf-8; subtype=xml", result.toString());
        assertEquals("UTF-8", result.getParameters().get("charset"));
        assertEquals("xml", result.getParameters().get("subtype"));
    }

    @Test
    public void testParseWithMultipleParametersAndQuotedValuesAndWhitespace() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("charset", "UTF-8");
        parameters.put("subtype", "xml");
        MediaType result = mediaType.parse("application/xml; charset=utf-8 ; subtype=xml");
        assertEquals("application/xml; charset=utf-8 ; subtype=xml", result.toString());
        assertEquals("UTF-8", result.getParameters().get("charset"));
        assertEquals("xml", result.getParameters().get("subtype"));
    }
}
