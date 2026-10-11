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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

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
