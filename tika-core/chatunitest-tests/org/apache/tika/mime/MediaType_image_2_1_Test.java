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

public class MediaType_image_2_1_Test {

    @Test
    public void testImageMethod() {
        MediaType imageType = MediaType.image("jpeg");
        assertNotNull(imageType);
        assertEquals("image/jpeg", imageType.toString());
        assertEquals("jpeg", imageType.getSubtype());
        assertEquals("image", imageType.getType());
        assertEquals(Collections.emptyMap(), imageType.getParameters());
    }

    @Test
    public void testImageMethodWithParameters() {
        MediaType imageType = MediaType.image("jpeg; charset=utf-8");
        assertNotNull(imageType);
        assertEquals("image/jpeg; charset=utf-8", imageType.toString());
        assertEquals("jpeg", imageType.getSubtype());
        assertEquals("image", imageType.getType());
        assertEquals("charset=utf-8", imageType.getParameters().get("charset"));
    }

    @Test
    public void testImageMethodWithInvalidType() {
        MediaType imageType = MediaType.image("invalid");
        assertNull(imageType);
    }

    @Test
    public void testImageMethodWithInvalidSubtype() {
        MediaType imageType = MediaType.image("jpeg; invalid");
        assertNull(imageType);
    }

    @Test
    public void testImageMethodWithInvalidParameters() {
        MediaType imageType = MediaType.image("jpeg; charset=invalid");
        assertNull(imageType);
    }

    @Test
    public void testImageMethodWithInvalidTypeAndSubtype() {
        MediaType imageType = MediaType.image("invalid; invalid");
        assertNull(imageType);
    }

    @Test
    public void testImageMethodWithInvalidTypeAndParameters() {
        MediaType imageType = MediaType.image("invalid; charset=invalid");
        assertNull(imageType);
    }

    @Test
    public void testImageMethodWithInvalidSubtypeAndParameters() {
        MediaType imageType = MediaType.image("jpeg; invalid=invalid");
        assertNull(imageType);
    }

    @Test
    public void testImageMethodWithInvalidTypeAndSubtypeAndParameters() {
        MediaType imageType = MediaType.image("invalid; invalid=invalid; charset=invalid");
        assertNull(imageType);
    }
}
