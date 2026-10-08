package org.apache.tika.mime;

import static org.junit.jupiter.params.provider.Arguments.*;
import static org.junit.jupiter.params.provider.CsvSource.*;
import static org.junit.jupiter.params.provider.MethodSource.*;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tika.mime.MediaType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class MediaType_application_0_0_Test {

    private MediaType mediaType;

    @BeforeEach
    public void setUp() {
        mediaType = null;
    }

    @ParameterizedTest
    @CsvSource({ "application/json", "text/plain", "application/xml", "application/zip", "application/octet-stream", "application/x-empty" })
    public void testValidApplication(String type) {
        mediaType = MediaType.application(type);
        assertNotNull(mediaType);
        assertEquals("application/" + type.toLowerCase(Locale.ENGLISH), mediaType.getType());
        assertEquals("", mediaType.getSubtype());
        assertEquals(Collections.emptyMap(), mediaType.getParameters());
    }

    @ParameterizedTest
    @CsvSource({ "application/json; charset=utf-8", "text/plain; charset=utf-8", "application/xml; charset=utf-8", "application/zip; charset=utf-8", "application/octet-stream; charset=utf-8", "application/x-empty; charset=utf-8" })
    public void testApplicationWithCharset(String type) {
        mediaType = MediaType.application(type);
        assertNotNull(mediaType);
        assertEquals("application/" + type.substring(0, type.indexOf(';')).toLowerCase(Locale.ENGLISH), mediaType.getType());
        assertEquals("", mediaType.getSubtype());
        assertEquals("charset=utf-8", mediaType.getParameters().get("charset"));
    }

    @ParameterizedTest
    @CsvSource({ "application/json; param1=value1; param2=value2", "text/plain; param1=value1; param2=value2", "application/xml; param1=value1; param2=value2", "application/zip; param1=value1; param2=value2", "application/octet-stream; param1=value1; param2=value2", "application/x-empty; param1=value1; param2=value2" })
    public void testApplicationWithParameters(String type) {
        mediaType = MediaType.application(type);
        assertNotNull(mediaType);
        assertEquals("application/" + type.substring(0, type.indexOf(';')).toLowerCase(Locale.ENGLISH), mediaType.getType());
        assertEquals("", mediaType.getSubtype());
        assertEquals("param1=value1; param2=value2", mediaType.getParameters().toString());
    }

    @ParameterizedTest
    @CsvSource({ "application/json; charset=utf-8; param1=value1", "text/plain; charset=utf-8; param1=value1", "application/xml; charset=utf-8; param1=value1", "application/zip; charset=utf-8; param1=value1", "application/octet-stream; charset=utf-8; param1=value1", "application/x-empty; charset=utf-8; param1=value1" })
    public void testApplicationWithCharsetAndParameters(String type) {
        mediaType = MediaType.application(type);
        assertNotNull(mediaType);
        assertEquals("application/" + type.substring(0, type.indexOf(';')).toLowerCase(Locale.ENGLISH), mediaType.getType());
        assertEquals("", mediaType.getSubtype());
        assertEquals("charset=utf-8; param1=value1", mediaType.getParameters().toString());
    }

    @Test
    public void testInvalidApplication() {
        mediaType = MediaType.application("invalid/type");
        assertNull(mediaType);
    }

    @Test
    public void testNullApplication() {
        mediaType = MediaType.application(null);
        assertNull(mediaType);
    }

    @Test
    public void testEmptyApplication() {
        mediaType = MediaType.application("");
        assertNull(mediaType);
    }

    @Test
    public void testApplicationWithSpecialChars() {
        mediaType = MediaType.application("application/json; charset=utf-8; param1=value1; param2=value2; special-characters");
        assertNotNull(mediaType);
        assertEquals("application/json", mediaType.getType());
    }
}
