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

package uk.jamierocks.mana.carbon.config;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents the key of which holds a configuration value.
 *
 * @param <T> The type of the value
 * @author Jamie Mansfield
 * @since 2.0.0
 */
public final class ConfigKey<T> {

    private final String[] path;

    private ConfigKey(String[] path) {
        this.path = path;
    }

    /**
     * Gets a {@link ConfigKey} of the given path.
     *
     * @param path The path
     * @param <T> The type of the key
     * @return The key
     * @since 2.0.0
     */
    public static <T> ConfigKey<T> of(String... path) {
        checkNotNull(path, "path is null!");
        return new ConfigKey<>(path);
    }

    /**
     * Gets the path to the configuration value.
     *
     * @return The path
     * @since 2.0.0
     */
    public final String[] getPath() {
        return this.path;
    }
}
