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
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MediaType_hasParameters_15_3_Test {

    @Test
    public void testHasParametersEmpty() {
        // Arrange
        MediaType mediaType = new MediaType("text", "plain", Collections.emptyMap());
        // Act
        boolean result = mediaType.hasParameters();
        // Assert
        assertFalse(result);
    }

    @Test
    public void testHasParametersNonEmpty() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        MediaType mediaType = new MediaType("text", "plain", parameters);
        // Act
        boolean result = mediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersNull() {
        // Arrange
        MediaType mediaType = new MediaType("text", "plain");
        // Act
        boolean result = mediaType.hasParameters();
        // Assert
        assertFalse(result);
    }

    @Test
    public void testHasParametersWithMultipleParameters() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value1");
        parameters.put("param2", "value2");
        MediaType mediaType = new MediaType("text", "plain", parameters);
        // Act
        boolean result = mediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithEmptyValue() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "");
        MediaType mediaType = new MediaType("text", "plain", parameters);
        // Act
        boolean result = mediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithSpecialCharactersInValue() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value with special chars: @,;:\"/\\[]?\\s");
        MediaType mediaType = new MediaType("text", "plain", parameters);
        // Act
        boolean result = mediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithMultipleSpecialCharactersInValue() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value with multiple special chars: @,;:\"/\\[]?\\s");
        MediaType mediaType = new MediaType("text", "plain", parameters);
        // Act
        boolean result = mediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithMultipleSpecialCharactersInValueAndEmptyValue() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value with multiple special chars: @,;:\"/\\[]?\\s");
        parameters.put("param2", "");
        MediaType mediaType = new MediaType("text", "plain", parameters);
        // Act
        boolean result = mediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithMultipleSpecialCharactersInValueAndNonEmptyValue() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value with multiple special chars: @,;:\"/\\[]?\\s");
        parameters.put("param2", "value2");
        MediaType mediaType = new MediaType("text", "plain", parameters);
        // Act
        boolean result = mediaType.hasParameters();
        // Assert
        assertTrue(result);
    }

    @Test
    public void testHasParametersWithMultipleSpecialCharactersInValueAndMultipleNonEmptyValues() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "value with multiple special chars: @,;:\"/\\[]?\\s");
        parameters.put("param2", "value2");
        parameters.put("param3", "value3");
        MediaType mediaType = new MediaType("text", "plain", parameters);
        // Act
        boolean result = mediaType.hasParameters();
        // Assert
        assertTrue(result);
    }
}
