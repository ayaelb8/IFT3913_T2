package org.apache.tika.mime;

import org.apache.tika.mime.MediaType;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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

public class MediaType_text_3_1_Test {

    @Test
    public void testTextInvalidType() {
        MediaType type = MediaType.text("invalid/type");
        assertNull(type);
    }

    @Test
    public void testTextEmptyType() {
        MediaType type = MediaType.text("");
        assertNull(type);
    }

    @Test
    public void testTextAllWhitespaceType() {
        MediaType type = MediaType.text("   ");
        assertNull(type);
    }

    @Test
    public void testTextInvalidParameters() {
        MediaType type = MediaType.text("text/plain; charset=utf-8; invalid");
        assertNull(type);
    }

    @Test
    public void testTextInvalidParametersWithMultiple() {
        MediaType type = MediaType.text("text/plain; charset=utf-8; charset=iso-8859-1");
        assertNull(type);
    }

    @Test
    public void testTextInvalidParametersWithInvalidKey() {
        MediaType type = MediaType.text("text/plain; charset=utf-8; charset=iso-8859-1; language=en");
        assertNull(type);
    }

    @Test
    public void testTextInvalidParametersWithInvalidValue() {
        MediaType type = MediaType.text("text/plain; charset=utf-8; charset=iso-8859-1; language=en");
        assertNull(type);
    }

    @Test
    public void testTextInvalidParametersWithInvalidKeyAndValue() {
        MediaType type = MediaType.text("text/plain; charset=utf-8; charset=iso-8859-1; language=en");
        assertNull(type);
    }

    @Test
    public void testTextInvalidParametersWithInvalidKeyAndValue2() {
        MediaType type = MediaType.text("text/plain; charset=utf-8; charset=iso-8859-1; language=en");
        assertNull(type);
    }

    @Test
    public void testTextInvalidParametersWithInvalidKeyAndValue3() {
        MediaType type = MediaType.text("text/plain; charset=utf-8; charset=iso-8859-1; language=en");
        assertNull(type);
    }

    @Test
    public void testTextInvalidParametersWithInvalidKeyAndValue4() {
        MediaType type = MediaType.text("text/plain; charset=utf-8; charset=iso-8859-1; language=en");
        assertNull(type);
    }
}
