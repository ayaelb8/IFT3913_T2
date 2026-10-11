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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertTrue(mediaType1.compareTo(mediaType4) > 0);
    }

    @Test
    public void testCompareToDifferentTypeSameParameters() {
        assertTrue(mediaType1.compareTo(mediaType3) > 0);
    }

    @Test
    public void testCompareToDifferentTypeDifferentParameters() {
        MediaType mediaType4 = new MediaType("application/json", "utf-16");
        assertTrue(mediaType1.compareTo(mediaType4) > 0);
    }

    @Test
    public void testCompareToNull() {
        assertThrows(NullPointerException.class, () -> mediaType1.compareTo(null));
    }
}
