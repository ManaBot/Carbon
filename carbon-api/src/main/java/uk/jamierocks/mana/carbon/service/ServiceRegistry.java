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

package uk.jamierocks.mana.carbon.service;

import java.util.Optional;

/**
 * A registry for services and their providers.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface ServiceRegistry {

    /**
     * Registers the given provider to the given service within the registry.
     *
     * @param plugin The instance of the plugin of which is registering the provider
     * @param service The service
     * @param provider The provider for the service
     * @param <T> The type of the service
     * @since 1.0.0
     */
    <T> void registerProvider(Object plugin, Class<T> service, T provider);

    /**
     * Returns the provider for the given service, if one is available.
     *
     * @param service The service
     * @param <T> The type of the service
     * @return The provider if available
     * @since 1.0.0
     */
    <T> Optional<T> provide(Class<T> service);

    /**
     * Returns the provider for the given service or the fallback if not available.
     *
     * @param service The service
     * @param <T> The type of the service
     * @return The provider if available
     * @since 2.0.0
     */
    default <T> T provideOrFallback(Class<T> service, T fallback) {
        final Optional<T> provider = this.provide(service);
        if (provider.isPresent()) {
            return provider.get();
        } else {
            return fallback;
        }
    }

    /**
     * Returns the registration for the given service, if one is available.
     *
     * @param service The service
     * @param <T> The type of the service
     * @return The registration if available
     * @since 1.0.0
     */
    <T> Optional<ProviderRegistration<T>> provideRegistration(Class<T> service);

    /**
     * Returns weather the given service has a registered provider.
     *
     * @param service The service
     * @return {@code True} if it does, {@code false} otherwise
     * @since 1.2.0
     */
    default boolean hasProvider(Class<?> service) {
        return this.provideRegistration(service).isPresent();
    }
}
