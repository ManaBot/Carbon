/*
 * This file is part of Carbon, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016, Jamie Mansfield <https://www.jamierocks.uk/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
