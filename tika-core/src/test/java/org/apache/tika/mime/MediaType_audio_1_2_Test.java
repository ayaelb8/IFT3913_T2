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

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

public class MediaType_audio_1_2_Test {

    @Test
    public void testAudioMethod() throws Exception {
        // Define the input type
        String type = "mp3";
        // Define the expected result
        MediaType expected = MediaType.parse("audio/mp3");
        // Appel de la méthode audio par réflexion
        Method audioMethod = MediaType.class.getDeclaredMethod("audio", String.class);
        audioMethod.setAccessible(true);
        MediaType result = (MediaType) audioMethod.invoke(null, type);
        // Verify the result
        assertEquals(expected, result);
    }

    @Test
    public void testAudioMethodWithCustomParameters() throws Exception {
        // Type en entrée, avec le paramètre personnalisé
        String type = "mp3; custom=value";
        // Define the expected result
        MediaType expected = MediaType.parse("audio/mp3; custom=value");
        // Appel de la méthode audio par réflexion
        Method audioMethod = MediaType.class.getDeclaredMethod("audio", String.class);
        audioMethod.setAccessible(true);
        MediaType result = (MediaType) audioMethod.invoke(null, type);
        // Verify the result
        assertEquals(expected, result);
    }

    @Test
    public void testAudioMethodWithNullType() throws Exception {
        // Define the input type
        String type = null;
        // Define the expected result
        MediaType expected = MediaType.parse("audio/null");
        // Appel de la méthode audio par réflexion
        Method audioMethod = MediaType.class.getDeclaredMethod("audio", String.class);
        audioMethod.setAccessible(true);
        MediaType result = (MediaType) audioMethod.invoke(null, type);
        // Verify the result
        assertEquals(expected, result);
    }

    @Test
    public void testAudioMethodWithEmptyType() throws Exception {
        // Define the input type
        String type = "";
        // Define the expected result
        MediaType expected = null;
        // Appel de la méthode audio par réflexion
        Method audioMethod = MediaType.class.getDeclaredMethod("audio", String.class);
        audioMethod.setAccessible(true);
        MediaType result = (MediaType) audioMethod.invoke(null, type);
        // Verify the result
        assertEquals(expected, result);
    }

    @Test
    public void testAudioMethodWithInvalidType() throws Exception {
        // Define the input type
        String type = "invalid";
        // Define the expected result
        MediaType expected = MediaType.parse("audio/invalid");
        // Appel de la méthode audio par réflexion
        Method audioMethod = MediaType.class.getDeclaredMethod("audio", String.class);
        audioMethod.setAccessible(true);
        MediaType result = (MediaType) audioMethod.invoke(null, type);
        // Verify the result
        assertEquals(expected, result);
    }
}
