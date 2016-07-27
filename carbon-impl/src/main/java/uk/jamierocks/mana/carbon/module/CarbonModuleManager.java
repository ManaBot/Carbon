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

package uk.jamierocks.mana.carbon.module;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import uk.jamierocks.mana.carbon.Carbon;
import uk.jamierocks.mana.carbon.CarbonImpl;
import uk.jamierocks.mana.carbon.guice.ModuleGuiceModule;
import uk.jamierocks.mana.carbon.plugin.PluginContainer;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * The implementation of {@link ModuleManager} for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class CarbonModuleManager implements ModuleManager {

    private final Map<String, ModuleContainer> modules = Maps.newHashMap();

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerModule(Object plugin, Class<?> module) {
        checkNotNull(module, "plugin is null!");
        checkNotNull(module, "module is null!");

        if (plugin instanceof PluginContainer) {
            this.registerModule0((PluginContainer) plugin, module);
        } else {
            Optional<PluginContainer> container = Carbon.getCarbon().getPluginManager().fromInstance(plugin);
            if (container.isPresent()) {
                this.registerModule0(container.get(), module);
            } else {
                CarbonImpl.LOGGER.error("Could not find container for the given plugin!");
            }
        }
    }

    private void registerModule0(PluginContainer container, Class<?> module) {
        if (module.isAnnotationPresent(Module.class)) {
            Module moduleAnnotation = module.getDeclaredAnnotation(Module.class);

            if (Carbon.getCarbon().getConfiguration().getNode()
                    .getNode("module", moduleAnnotation.id(), "enabled").getBoolean(false)) {
                CarbonImpl.LOGGER
                        .info("Loading module: " + moduleAnnotation.name() + " (" + moduleAnnotation.id() + ")");

                Injector injector = Guice.createInjector(new ModuleGuiceModule(moduleAnnotation));
                Object instance = injector.getInstance(module);

                Carbon.getCarbon().getEventBus().register(instance);
                this.modules.put(moduleAnnotation.id(), ModuleContainer.of(moduleAnnotation, instance, container));

                CarbonImpl.LOGGER
                        .info("Loaded module: " + moduleAnnotation.name() + " (" + moduleAnnotation.id() + ")");
            }
        } else {
            CarbonImpl.LOGGER.error(module.getName() + " has no @Module annotation!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ModuleContainer> getModule(String id) {
        checkNotNull(id, "id is null!");
        return Optional.ofNullable(this.modules.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<ModuleContainer> getModules() {
        return Collections.unmodifiableCollection(this.modules.values());
    }
}
