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
            final Module moduleAnnotation = module.getDeclaredAnnotation(Module.class);

            if (Carbon.getCarbon().getConfiguration().getNode().getNode("module", moduleAnnotation.id(), "enabled").getBoolean(true)) {
                CarbonImpl.LOGGER.info("Loading module: " + moduleAnnotation.name() + " (" + moduleAnnotation.id() + ")");

                Injector injector = Guice.createInjector(new ModuleGuiceModule(container, moduleAnnotation));
                Object instance = injector.getInstance(module);

                Carbon.getCarbon().getEventBus().register(instance);
                this.modules.put(moduleAnnotation.id(), ModuleContainer.of(moduleAnnotation, instance, container));

                CarbonImpl.LOGGER.info("Loaded module: " + moduleAnnotation.name() + " (" + moduleAnnotation.id() + ")");
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
