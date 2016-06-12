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

package uk.jamierocks.mana.carbon;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Maps;
import uk.jamierocks.mana.carbon.service.ProviderRegistration;
import uk.jamierocks.mana.carbon.service.ProviderRegistrationException;
import uk.jamierocks.mana.carbon.service.ServiceRegistry;

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

    protected CarbonServiceRegistry() {}

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
            Carbon.getCarbon().getLogger().error("Failed to register provider!", e);
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
