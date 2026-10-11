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

import java.util.Collections;

import org.junit.jupiter.api.Test;

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
        assertEquals("utf-8", imageType.getParameters().get("charset"));
    }

    @Test
    public void testImageMethodWithInvalidType() {
        MediaType imageType = MediaType.image("invalid");
        assertEquals("image/invalid", imageType.toString());
    }

    @Test
    public void testImageMethodWithInvalidSubtype() {
        MediaType imageType = MediaType.image("jpeg; invalid");
        assertEquals("image/jpeg; invalid=", imageType.toString());
    }

    @Test
    public void testImageMethodWithInvalidParameters() {
        MediaType imageType = MediaType.image("jpeg; charset=invalid");
        assertEquals("image/jpeg; charset=invalid", imageType.toString());
    }

    @Test
    public void testImageMethodWithInvalidTypeAndSubtype() {
        MediaType imageType = MediaType.image("invalid; invalid");
        assertEquals("image/invalid; invalid=", imageType.toString());
    }

    @Test
    public void testImageMethodWithInvalidTypeAndParameters() {
        MediaType imageType = MediaType.image("invalid; charset=invalid");
        assertEquals("image/invalid; charset=invalid", imageType.toString());
    }

    @Test
    public void testImageMethodWithInvalidSubtypeAndParameters() {
        MediaType imageType = MediaType.image("jpeg; invalid=invalid");
        assertEquals("image/jpeg; invalid=invalid", imageType.toString());
    }

    @Test
    public void testImageMethodWithInvalidTypeAndSubtypeAndParameters() {
        MediaType imageType = MediaType.image("invalid; invalid=invalid; charset=invalid");
        assertEquals("image/invalid; charset=invalid; invalid=invalid", imageType.toString());
    }
}
