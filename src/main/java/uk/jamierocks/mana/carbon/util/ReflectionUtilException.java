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

/**
 * An exception for any issues that may be encountered in {@link ReflectionUtil}.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public class ReflectionUtilException extends Exception {

    /**
     * Constructs the exception with only a message.
     *
     * @param message The message
     * @since 1.0.0
     */
    public ReflectionUtilException(String message) {
        super(message);
    }

    /**
     * Constructs the exception with only a cause.
     *
     * @param cause The cause
     * @since 1.0.0
     */
    public ReflectionUtilException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs the exception with a message and cause.
     *
     * @param message The message
     * @param cause The cause
     * @since 1.0.0
     */
    public ReflectionUtilException(String message, Throwable cause) {
        super(message, cause);
    }
}
