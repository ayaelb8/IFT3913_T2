package org.apache.tika.mime;

import org.apache.tika.mime.MediaType;
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
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class MediaType_equals_18_1_Test {

    @Test
    public void testEqualsWithSameInstance() {
        MediaType type = new MediaType("text/plain", "utf-8");
        assertTrue(type.equals(type));
    }

    @Test
    public void testEqualsWithDifferentInstances() {
        MediaType type1 = new MediaType("text/plain", "utf-8");
        MediaType type2 = new MediaType("text/plain", "utf-8");
        assertTrue(type1.equals(type2));
    }

    @Test
    public void testEqualsWithDifferentTypes() {
        MediaType type1 = new MediaType("text/plain", "utf-8");
        MediaType type2 = new MediaType("application/json", "utf-8");
        assertFalse(type1.equals(type2));
    }

    @Test
    public void testEqualsWithDifferentSubtypes() {
        MediaType type1 = new MediaType("text/plain", "utf-8");
        MediaType type2 = new MediaType("text/plain", "ascii");
        assertFalse(type1.equals(type2));
    }

    @Test
    public void testEqualsWithDifferentParameters() {
        MediaType type1 = new MediaType("text/plain", "utf-8", Collections.singletonMap("charset", "utf-8"));
        MediaType type2 = new MediaType("text/plain", "utf-8", Collections.singletonMap("charset", "ascii"));
        assertFalse(type1.equals(type2));
    }

    @Test
    public void testEqualsWithDifferentOrderOfParameters() {
        MediaType type1 = new MediaType("text/plain", "utf-8", Collections.singletonMap("charset", "utf-8"));
        MediaType type2 = new MediaType("text/plain", "utf-8", Collections.singletonMap("utf-8", "charset"));
        assertFalse(type1.equals(type2));
    }

    @Test
    public void testEqualsWithDifferentTypesAndSubtypes() {
        MediaType type1 = new MediaType("application/json", "utf-8");
        MediaType type2 = new MediaType("text/plain", "utf-8");
        assertFalse(type1.equals(type2));
    }

    @Test
    public void testEqualsWithDifferentTypesAndParameters() {
        MediaType type1 = new MediaType("application/json", "utf-8", Collections.singletonMap("charset", "utf-8"));
        MediaType type2 = new MediaType("text/plain", "utf-8", Collections.singletonMap("charset", "ascii"));
        assertFalse(type1.equals(type2));
    }

    @Test
    public void testEqualsWithDifferentSubtypesAndParameters() {
        MediaType type1 = new MediaType("text/plain", "utf-8", Collections.singletonMap("charset", "utf-8"));
        MediaType type2 = new MediaType("text/plain", "ascii", Collections.singletonMap("charset", "utf-8"));
        assertFalse(type1.equals(type2));
    }

    @Test
    public void testEqualsWithDifferentTypesAndSubtypesAndParameters() {
        MediaType type1 = new MediaType("application/json", "utf-8", Collections.singletonMap("charset", "utf-8"));
        MediaType type2 = new MediaType("text/plain", "ascii", Collections.singletonMap("charset", "ascii"));
        assertFalse(type1.equals(type2));
    }

    @Test
    public void testEqualsWithDifferentTypesAndSubtypesAndParametersAndCase() {
        MediaType type1 = new MediaType("application/json", "utf-8", Collections.singletonMap("charset", "utf-8"));
        MediaType type2 = new MediaType("text/plain", "ascii", Collections.singletonMap("CHARSET", "utf-8"));
        assertFalse(type1.equals(type2));
    }
}
