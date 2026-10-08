package org.apache.tika.mime;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MediaType_compareTo_20_1_Test {

    private MediaType mediaType1;

    private MediaType mediaType2;

    private MediaType mediaType3;

    @BeforeEach
    public void setUp() {
        mediaType1 = new MediaType("text/plain", "utf-8");
        mediaType2 = new MediaType("text/plain", "utf-8");
        mediaType3 = new MediaType("application/json", "utf-8");
    }

    @Test
    public void testCompareToSameTypeSameParameters() {
        assertEquals(0, mediaType1.compareTo(mediaType2));
    }

    @Test
    public void testCompareToSameTypeDifferentParameters() {
        MediaType mediaType4 = new MediaType("text/plain", "utf-16");
        assertThrows(NullPointerException.class, () -> mediaType1.compareTo(mediaType4));
    }

    @Test
    public void testCompareToDifferentTypeSameParameters() {
        assertEquals(-1, mediaType1.compareTo(mediaType3));
    }

    @Test
    public void testCompareToDifferentTypeDifferentParameters() {
        MediaType mediaType4 = new MediaType("application/json", "utf-16");
        assertThrows(NullPointerException.class, () -> mediaType1.compareTo(mediaType4));
    }

    @Test
    public void testCompareToNull() {
        assertThrows(NullPointerException.class, () -> mediaType1.compareTo(null));
    }
}
