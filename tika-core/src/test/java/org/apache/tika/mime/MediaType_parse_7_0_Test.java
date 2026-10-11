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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MediaType_parse_7_0_Test {

    @ParameterizedTest
    @CsvSource(nullValues = "null", value = { "application/octet-stream, application/octet-stream", "text/plain, text/plain",
            "text/html, text/html", "application/xml, application/xml", "application/zip, application/zip", "invalid, null",
            " , null", "application/xml; charset=utf-8, application/xml; charset=utf-8",
            "application/xml; charset=ISO-8859-1, application/xml; charset=ISO-8859-1",
            "application/xml; charset=utf-8; subtype=xml, application/xml; charset=utf-8; subtype=xml" })
    public void testParse(String input, String expected) {
        MediaType result = MediaType.parse(input);
        if (expected != null) {
            assertEquals(expected, result.toString());
        } else {
            assertNull(result);
        }
    }

    @Test
    public void testParseWithInvalidInput() {
        assertNull(MediaType.parse(null));
    }

    @Test
    public void testParseWithMultipleParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("charset", "UTF-8");
        parameters.put("subtype", "xml");
        MediaType result = MediaType.parse("application/xml; charset=utf-8; subtype=xml");
        assertEquals("application/xml; charset=utf-8; subtype=xml", result.toString());
        assertEquals("utf-8", result.getParameters().get("charset"));
        assertEquals("xml", result.getParameters().get("subtype"));
    }

    @Test
    public void testParseWithQuotedValue() {
        MediaType result = MediaType.parse("text/plain; charset=\"UTF-8\"");
        assertEquals("text/plain; charset=UTF-8", result.toString());
        assertEquals("UTF-8", result.getParameters().get("charset"));
    }

    @Test
    public void testParseWithMultipleQuotedValues() {
        MediaType result = MediaType.parse("text/plain; charset=\"UTF-8\"; subtype=\"xml\"");
        assertEquals("text/plain; charset=UTF-8; subtype=xml", result.toString());
        assertEquals("UTF-8", result.getParameters().get("charset"));
        assertEquals("xml", result.getParameters().get("subtype"));
    }

    @Test
    public void testParseWithMultipleParametersAndQuotedValues() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("charset", "UTF-8");
        parameters.put("subtype", "xml");
        MediaType result = MediaType.parse("application/xml; charset=utf-8; subtype=xml");
        assertEquals("application/xml; charset=utf-8; subtype=xml", result.toString());
        assertEquals("utf-8", result.getParameters().get("charset"));
        assertEquals("xml", result.getParameters().get("subtype"));
    }

    @Test
    public void testParseWithMultipleParametersAndQuotedValuesAndWhitespace() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("charset", "UTF-8");
        parameters.put("subtype", "xml");
        MediaType result = MediaType.parse("application/xml; charset=utf-8 ; subtype=xml");
        assertEquals("application/xml; charset=utf-8; subtype=xml", result.toString());
        assertEquals("utf-8", result.getParameters().get("charset"));
        assertEquals("xml", result.getParameters().get("subtype"));
    }
}
