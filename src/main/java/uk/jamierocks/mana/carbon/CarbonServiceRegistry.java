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

package uk.jamierocks.mana.carbon;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Maps;
import uk.jamierocks.mana.carbon.service.ProviderRegistration;
import uk.jamierocks.mana.carbon.service.ProviderRegistrationException;
import uk.jamierocks.mana.carbon.service.ServiceRegistry;
import uk.jamierocks.mana.carbon.service.exception.ExceptionReporter;

import java.util.Map;
import java.util.Optional;

/**
 * The implementation of {@link ServiceRegistry} for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class CarbonServiceRegistry implements ServiceRegistry {

    private final Map<Class<?>, ProviderRegistration<?>> providers = Maps.newHashMap();

    protected CarbonServiceRegistry() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void registerProvider(Object plugin, Class<T> service, T provider) {
        checkNotNull(plugin, "plugin is null!");
        checkNotNull(service, "service is null!");
        checkNotNull(provider, "provider is null!");

        try {
            this.providers.put(service, ProviderRegistration.of(plugin, service, provider));
        } catch (ProviderRegistrationException e) {
            ExceptionReporter.report("Failed to register provider!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Optional<T> provide(Class<T> service) {
        checkNotNull(service, "service is null!");

        Optional<ProviderRegistration<T>> providerRegistration = this.provideRegistration(service);
        if (providerRegistration.isPresent()) {
            return Optional.of(providerRegistration.get().getProvider());
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Optional<ProviderRegistration<T>> provideRegistration(Class<T> service) {
        checkNotNull(service, "service is null!");
        return Optional.ofNullable((ProviderRegistration<T>) this.providers.get(service));
    }
}
