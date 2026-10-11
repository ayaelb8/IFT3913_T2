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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MediaType_application_0_0_Test {

    private MediaType mediaType;

    @BeforeEach
    public void setUp() {
        mediaType = null;
    }

    @ParameterizedTest
    @CsvSource({ "json", "plain", "xml", "zip", "octet-stream", "x-empty" })
    public void testValidApplication(String type) {
        mediaType = MediaType.application(type);
        assertNotNull(mediaType);
        assertEquals("application/" + type.toLowerCase(Locale.ENGLISH), mediaType.toString());
        assertEquals(type, mediaType.getSubtype());
        assertEquals(Collections.emptyMap(), mediaType.getParameters());
    }

    @ParameterizedTest
    @CsvSource({ "json; charset=utf-8", "plain; charset=utf-8", "xml; charset=utf-8", "zip; charset=utf-8",
            "octet-stream; charset=utf-8", "x-empty; charset=utf-8" })
    public void testApplicationWithCharset(String type) {
        mediaType = MediaType.application(type);
        assertNotNull(mediaType);
        assertEquals("application/" + type.substring(0, type.indexOf(';')).toLowerCase(Locale.ENGLISH),
                mediaType.getBaseType().toString());
        assertEquals(type.substring(0, type.indexOf(';')), mediaType.getSubtype());
        assertEquals("utf-8", mediaType.getParameters().get("charset"));
    }

    @ParameterizedTest
    @CsvSource({ "json; param1=value1; param2=value2", "plain; param1=value1; param2=value2",
            "xml; param1=value1; param2=value2", "zip; param1=value1; param2=value2",
            "octet-stream; param1=value1; param2=value2", "x-empty; param1=value1; param2=value2" })
    public void testApplicationWithParameters(String type) {
        mediaType = MediaType.application(type);
        assertNotNull(mediaType);
        assertEquals("application/" + type.substring(0, type.indexOf(';')).toLowerCase(Locale.ENGLISH),
                mediaType.getBaseType().toString());
        assertEquals(type.substring(0, type.indexOf(';')), mediaType.getSubtype());
        assertEquals("{param1=value1, param2=value2}", mediaType.getParameters().toString());
    }

    @ParameterizedTest
    @CsvSource({ "json; charset=utf-8; param1=value1", "plain; charset=utf-8; param1=value1",
            "xml; charset=utf-8; param1=value1", "zip; charset=utf-8; param1=value1",
            "octet-stream; charset=utf-8; param1=value1", "x-empty; charset=utf-8; param1=value1" })
    public void testApplicationWithCharsetAndParameters(String type) {
        mediaType = MediaType.application(type);
        assertNotNull(mediaType);
        assertEquals("application/" + type.substring(0, type.indexOf(';')).toLowerCase(Locale.ENGLISH),
                mediaType.getBaseType().toString());
        assertEquals(type.substring(0, type.indexOf(';')), mediaType.getSubtype());
        assertEquals("{charset=utf-8, param1=value1}", mediaType.getParameters().toString());
    }

    @Test
    public void testInvalidApplication() {
        mediaType = MediaType.application("invalid/type");
        assertNull(mediaType);
    }

    @Test
    public void testNullApplication() {
        mediaType = MediaType.application(null);
        assertEquals("application/null", mediaType.toString());
    }

    @Test
    public void testEmptyApplication() {
        mediaType = MediaType.application("");
        assertNull(mediaType);
    }

    @Test
    public void testApplicationWithSpecialChars() {
        mediaType = MediaType.application("json; charset=utf-8; param1=value1; param2=value2; special-characters");
        assertNotNull(mediaType);
        assertEquals("application/json", mediaType.getBaseType().toString());
    }
}
