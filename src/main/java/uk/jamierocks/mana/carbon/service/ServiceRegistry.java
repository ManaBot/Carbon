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
     * @since 1.3.0
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
