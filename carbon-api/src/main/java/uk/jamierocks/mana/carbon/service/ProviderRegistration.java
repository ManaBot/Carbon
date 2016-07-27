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

import static com.google.common.base.Preconditions.checkNotNull;

import uk.jamierocks.mana.carbon.Carbon;
import uk.jamierocks.mana.carbon.plugin.PluginContainer;

import java.util.Optional;

/**
 * Represents all the information for the registration of a provider for a service.
 *
 * @param <T> The type of the service
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface ProviderRegistration<T> {

    /**
     * Constructs a registration with the given value.
     *
     * @param plugin The instance of the plugin of which is registering the provider
     * @param service The service
     * @param provider The provider for the service
     * @param <T> The type of the service
     * @return A plugin registration
     * @throws ProviderRegistrationException If a {@link PluginContainer} is not found for the given plugin instance
     * @since 1.0.0
     */
    static <T> ProviderRegistration<T> of(Object plugin, Class<T> service, T provider) throws ProviderRegistrationException {
        checkNotNull(plugin, "plugin is null!");
        checkNotNull(service, "service is null!");
        checkNotNull(provider, "provider is null!");

        Optional<PluginContainer> pluginContainer = Carbon.getCarbon().getPluginManager().fromInstance(plugin);
        if (!pluginContainer.isPresent()) {
            throw new ProviderRegistrationException("PluginContainer not found for: " + plugin.getClass().getName());
        }

        return new ProviderRegistration<T>() {
            @Override
            public Class<T> getService() {
                return service;
            }

            @Override
            public T getProvider() {
                return provider;
            }

            @Override
            public PluginContainer getPlugin() {
                return pluginContainer.get();
            }
        };
    }

    /**
     * Gets the service of the registration.
     *
     * @return The service
     * @since 1.0.0
     */
    Class<T> getService();

    /**
     * Gets the provider of this registration.
     *
     * @return The provider
     * @since 1.0.0
     */
    T getProvider();

    /**
     * Gets the {@link PluginContainer} that registered this provider.
     *
     * @return The registrant
     * @since 1.0.0
     */
    PluginContainer getPlugin();
}
