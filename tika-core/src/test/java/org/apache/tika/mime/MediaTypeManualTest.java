/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.mime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

public class MediaTypeManualTest {

    private static final AtomicInteger NAME_COUNTER = new AtomicInteger();

    private static final int CACHE_LIMIT = 10000;

    @Test
    public void testFactoryMethodsPrefixSubtype() {
        assertEquals("application/pdf", MediaType.application("pdf").toString());
        assertEquals("audio/mpeg", MediaType.audio("mpeg").toString());
        assertEquals("image/png", MediaType.image("png").toString());
        assertEquals("text/csv", MediaType.text("csv").toString());
        assertEquals("video/mp4", MediaType.video("mp4").toString());
    }

    @Test
    public void testFactoryMethodsAcceptUnknownSubtype() {
        assertEquals("image/x-unknown-format", MediaType.image("x-unknown-format").toString());
    }

    @Test
    public void testFactoryMethodsReturnNullForInvalidSubtype() {
        assertNull(MediaType.text(""));
        assertNull(MediaType.text("a/b"));
    }

    @Test
    public void testSetOfTypesSkipsNullsAndDuplicates() {
        Set<MediaType> set = MediaType.set(MediaType.TEXT_PLAIN, null, MediaType.TEXT_HTML,
                MediaType.TEXT_PLAIN);

        assertEquals(2, set.size());
        assertTrue(set.contains(MediaType.TEXT_PLAIN));
        assertTrue(set.contains(MediaType.TEXT_HTML));
    }

    @Test
    public void testSetOfStringsSkipsUnparseableEntries() {
        Set<MediaType> set = MediaType.set("text/plain", "not a media type", null, "image/png");

        assertEquals(2, set.size());
        assertTrue(set.contains(MediaType.TEXT_PLAIN));
        assertTrue(set.contains(MediaType.image("png")));
    }

    @Test
    public void testSetIsUnmodifiable() {
        Set<MediaType> fromTypes = MediaType.set(MediaType.TEXT_PLAIN);
        Set<MediaType> fromStrings = MediaType.set("text/plain");

        assertThrows(UnsupportedOperationException.class,
                () -> fromTypes.add(MediaType.TEXT_HTML));
        assertThrows(UnsupportedOperationException.class,
                () -> fromStrings.add(MediaType.TEXT_HTML));
    }

    @Test
    public void testParseNullReturnsNull() {
        assertNull(MediaType.parse(null));
    }

    @Test
    public void testParseWithoutSlashReturnsNull() {
        assertNull(MediaType.parse("text"));
        assertNull(MediaType.parse(""));
    }

    @Test
    public void testParseEmptyTypeOrSubtypeReturnsNull() {
        assertNull(MediaType.parse("/plain"));
        assertNull(MediaType.parse("text/"));
        assertNull(MediaType.parse("/"));
    }

    @Test
    public void testParseReservedCharacterReturnsNull() {
        assertNull(MediaType.parse("te:xt/plain"));
        assertNull(MediaType.parse("text/pl ain"));
        assertNull(MediaType.parse("text/plain/extra"));
    }

    @Test
    public void testParseSimpleTypeIsCached() {
        String string = "x-manual/" + neverParsedName();
        MediaType first = MediaType.parse(string);
        MediaType second = MediaType.parse(string);

        assertEquals(string, first.toString());
        assertSame(first, second);
    }

    @Test
    public void testParseSimpleNameBoundaryCharacters() {
        String[] simple = {"a0/z9", "x09/az", "a-b/c+d", "a.b/c_d", "0/9", "-/+", "./_"};
        for (String prefix : simple) {
            String string = prefix + neverParsedName();
            MediaType type = MediaType.parse(string);
            assertEquals(string, type.toString());
            assertSame(type, MediaType.parse(string), string);
        }
    }

    private static String neverParsedName() {
        return "n" + Long.toString(System.nanoTime(), 36).replace('-', 'm')
                + "x" + NAME_COUNTER.incrementAndGet();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testParseStopsCachingAtLimit() throws Exception {
        Field field = MediaType.class.getDeclaredField("SIMPLE_TYPES");
        field.setAccessible(true);
        Map<String, MediaType> cache = (Map<String, MediaType>) field.get(null);

        synchronized (cache) {
            Map<String, MediaType> saved = new HashMap<>(cache);
            try {
                cache.clear();
                for (int i = 0; i < CACHE_LIMIT - 1; i++) {
                    cache.put("x-fill/n" + i, MediaType.OCTET_STREAM);
                }

                String lastCached = "x-manual/" + neverParsedName();
                assertSame(MediaType.parse(lastCached), MediaType.parse(lastCached));
                assertEquals(CACHE_LIMIT, cache.size());

                String notCached = "x-manual/" + neverParsedName();
                MediaType first = MediaType.parse(notCached);
                MediaType second = MediaType.parse(notCached);
                assertEquals(notCached, first.toString());
                assertEquals(first, second);
                assertNotSame(first, second);
                assertEquals(CACHE_LIMIT, cache.size());
            } finally {
                cache.clear();
                cache.putAll(saved);
            }
        }
    }

    @Test
    public void testParseNonSimpleNameIsNotCached() {
        String[] nonSimple = {"te`xt/manual", "te{xt/manual", "text/man`ual", "text/man{ual"};
        for (String string : nonSimple) {
            MediaType type = MediaType.parse(string);
            assertEquals(string, type.toString());
            assertNotSame(type, MediaType.parse(string), string);
            assertEquals(type, MediaType.parse(string), string);
        }

        MediaType upper = MediaType.parse("TEXT/Manual");
        assertEquals("text/manual", upper.toString());
        assertNotSame(upper, MediaType.parse("TEXT/Manual"));
    }

    @Test
    public void testParseTrimsWhitespaceAroundNames() {
        assertEquals("text/plain", MediaType.parse("  text / plain  ").toString());
    }

    @Test
    public void testParseCharsetFirst() {
        MediaType type = MediaType.parse("charset=UTF-8; text/html");

        assertEquals("text/html; charset=UTF-8", type.toString());
        assertEquals("text", type.getType());
        assertEquals("html", type.getSubtype());
        assertEquals(Collections.singletonMap("charset", "UTF-8"), type.getParameters());
    }

    @Test
    public void testDerivedTypeAddsParametersToPlainType() {
        MediaType type = new MediaType(MediaType.TEXT_PLAIN,
                Collections.singletonMap("charset", "UTF-8"));

        assertEquals("text/plain; charset=UTF-8", type.toString());
    }

    @Test
    public void testDerivedTypeKeepsParametersWhenNoneAdded() {
        MediaType base = MediaType.parse("text/plain; charset=UTF-8");
        MediaType type = new MediaType(base, Collections.emptyMap());

        assertEquals("text/plain; charset=UTF-8", type.toString());
        assertEquals(base, type);
    }

    @Test
    public void testDerivedTypeMergesAndOverridesParameters() {
        MediaType base = MediaType.parse("text/plain; charset=UTF-8; format=flowed");
        Map<String, String> added = new HashMap<>();
        added.put("charset", "ISO-8859-1");
        added.put("delsp", "yes");

        MediaType type = new MediaType(base, added);

        assertEquals("text/plain; charset=ISO-8859-1; delsp=yes; format=flowed",
                type.toString());
        assertEquals(3, type.getParameters().size());
    }

    @Test
    public void testDerivedTypeWithSingleParameter() {
        MediaType type = new MediaType(MediaType.TEXT_PLAIN, "format", "flowed");

        assertEquals("text/plain; format=flowed", type.toString());
    }

    @Test
    public void testDerivedTypeWithCharset() {
        MediaType type = new MediaType(MediaType.TEXT_HTML, StandardCharsets.ISO_8859_1);

        assertEquals("text/html; charset=ISO-8859-1", type.toString());
    }

    @Test
    public void testGetTypeAndSubtype() {
        MediaType plain = MediaType.parse("application/vnd.ms-excel");
        assertEquals("application", plain.getType());
        assertEquals("vnd.ms-excel", plain.getSubtype());

        MediaType withParameters = MediaType.parse("text/plain; charset=UTF-8");
        assertEquals("text", withParameters.getType());
        assertEquals("plain", withParameters.getSubtype());
    }

    @Test
    public void testGetBaseTypeWithoutParametersReturnsSameInstance() {
        MediaType type = MediaType.parse("image/png");

        assertSame(type, type.getBaseType());
    }

    @Test
    public void testGetBaseTypeDropsParameters() {
        MediaType type = MediaType.parse("text/plain; charset=UTF-8");
        MediaType base = type.getBaseType();

        assertEquals(MediaType.TEXT_PLAIN, base);
        assertEquals("text/plain", base.toString());
        assertFalse(base.hasParameters());
    }

    @Test
    public void testHasParameters() {
        assertFalse(MediaType.TEXT_PLAIN.hasParameters());
        assertTrue(MediaType.parse("text/plain; charset=UTF-8").hasParameters());
    }

    @Test
    public void testGetParametersIsSortedAndUnmodifiable() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Zeta", "1");
        parameters.put("alpha", "2");
        parameters.put("Mid", "3");
        MediaType type = new MediaType("text", "plain", parameters);

        List<String> names = new ArrayList<>(type.getParameters().keySet());
        assertEquals(List.of("alpha", "mid", "zeta"), names);
        assertThrows(UnsupportedOperationException.class,
                () -> type.getParameters().put("new", "value"));
        assertThrows(UnsupportedOperationException.class,
                () -> MediaType.TEXT_PLAIN.getParameters().put("new", "value"));
    }

    @Test
    public void testEqualsComparesNormalizedForm() {
        MediaType parsed = MediaType.parse("text/plain; charset=UTF-8");
        MediaType built = new MediaType("TEXT", " plain ",
                Collections.singletonMap("CHARSET", "UTF-8"));

        assertEquals(parsed, built);
        assertEquals(built, parsed);
        assertEquals(parsed, parsed);
    }

    @Test
    public void testEqualsDetectsDifferences() {
        MediaType type = MediaType.parse("text/plain; charset=UTF-8");

        assertNotEquals(MediaType.TEXT_PLAIN, type);
        assertNotEquals(MediaType.parse("text/html; charset=UTF-8"), type);
        assertNotEquals(MediaType.parse("text/plain; charset=UTF-16"), type);
        assertNotEquals(MediaType.parse("text/plain; charset=utf-8"), type);
    }

    @Test
    public void testEqualsRejectsNullAndOtherClasses() {
        assertFalse(MediaType.TEXT_PLAIN.equals(null));
        assertFalse(MediaType.TEXT_PLAIN.equals("text/plain"));
    }

    @Test
    public void testHashCodeIsConsistentWithEquals() {
        MediaType parsed = MediaType.parse("text/plain; charset=UTF-8");
        MediaType built = new MediaType("text", "plain",
                Collections.singletonMap("charset", "UTF-8"));

        assertEquals(parsed.hashCode(), built.hashCode());
        assertNotEquals(MediaType.TEXT_PLAIN.hashCode(), MediaType.TEXT_HTML.hashCode());
    }

    @Test
    public void testCompareToOrdersByStringForm() {
        MediaType pdf = MediaType.application("pdf");
        MediaType plain = MediaType.TEXT_PLAIN;

        assertTrue(pdf.compareTo(plain) < 0);
        assertTrue(plain.compareTo(pdf) > 0);
        assertEquals(0, plain.compareTo(MediaType.parse("text/plain")));
        assertTrue(plain.compareTo(MediaType.parse("text/plain; charset=UTF-8")) < 0);
    }

    @Test
    public void testCompareToNullThrows() {
        assertThrows(NullPointerException.class, () -> MediaType.TEXT_PLAIN.compareTo(null));
    }
}
