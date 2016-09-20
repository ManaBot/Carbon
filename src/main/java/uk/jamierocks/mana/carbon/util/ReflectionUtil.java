/*
 * Copyright 2016 Jamie Mansfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.jamierocks.mana.carbon.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * A utility for using Reflection.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class ReflectionUtil {

    /**
     * Sets the value of a <code>static final</code> field in the given class.
     *
     * @param clazz The class
     * @param field The name of the field
     * @param object The new value
     * @throws ReflectionUtilException If something goes wrong
     * @since 1.0.0
     */
    public static void setStaticFinal(Class clazz, String field, Object object) throws ReflectionUtilException {
        try {
            Field instanceField = clazz.getDeclaredField(field);
            instanceField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(instanceField, instanceField.getModifiers() & ~Modifier.FINAL);

            instanceField.set(null, object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ReflectionUtilException("Failed to set field: " + field + " in class: " + clazz.getName(), e);
        }
    }
}
