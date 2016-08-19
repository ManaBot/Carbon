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
