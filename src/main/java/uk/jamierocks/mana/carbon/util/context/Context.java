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

package uk.jamierocks.mana.carbon.util.context;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

/**
 * Represents the context of something.
 * A good example of this is with permissions, you may want to check them with a specific context.
 * For example: do they have permission x.y.z for #abc channel on the AYCNet server.
 *
 * @author Jamie Mansfield
 * @since 1.2.0
 */
public interface Context {

    /**
     * Gets a builder for {@link Context}.
     *
     * @return A builder
     * @since 1.2.0
     */
    static Builder builder() {
        return new Builder() {
            final Map contexts = Maps.newHashMap();

            @Override
            public <T> Builder set(Class<T> clazz, T obj) {
                this.contexts.put(clazz, obj);
                return this;
            }

            @Override
            public Context build() {
                return new Context() {
                    @Override
                    public <T> Optional<T> get(Class<T> clazz) {
                        return Optional.ofNullable((T) contexts.get(clazz));
                    }
                };
            }
        };
    }

    /**
     * Gets an object that is apart the context, if available.
     *
     * @param clazz The class
     * @param <T> The class type
     * @return The object
     * @since 1.2.0
     */
    <T> Optional<T> get(Class<T> clazz);

    /**
     * A builder for {@link Context}.
     *
     * @author Jamie Mansfield
     * @since 1.2.0
     */
    interface Builder {

        /**
         * Sets the given value as a context.
         *
         * @param clazz The class
         * @param obj The object
         * @param <T> The class type
         * @return The Builder
         * @since 1.2.0
         */
        <T> Builder set(Class<T> clazz, T obj);

        /**
         * Builds the {@link Context} from the given values.
         *
         * @return The context
         * @since 1.2.0
         */
        Context build();
    }
}
