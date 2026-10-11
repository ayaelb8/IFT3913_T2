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

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

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
