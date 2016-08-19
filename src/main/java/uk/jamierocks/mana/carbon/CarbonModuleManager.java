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
import com.google.inject.Guice;
import com.google.inject.Injector;
import uk.jamierocks.mana.carbon.module.Module;
import uk.jamierocks.mana.carbon.module.ModuleContainer;
import uk.jamierocks.mana.carbon.module.ModuleManager;
import uk.jamierocks.mana.carbon.plugin.PluginContainer;
import uk.jamierocks.mana.carbon.util.guice.ModuleGuiceModule;

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

    protected CarbonModuleManager() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerModule(Class<?> module) {
        checkNotNull(module, "module is null!");
        this.registerModule0(null, module);
    }

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
                Carbon.getCarbon().getLogger().error("Could not find container for the given plugin!");
            }
        }
    }

    private void registerModule0(PluginContainer container, Class<?> module) {
        if (module.isAnnotationPresent(Module.class)) {
            Module moduleAnnotation = module.getDeclaredAnnotation(Module.class);

            if (Carbon.getCarbon().getConfigurationNode()
                    .getNode("module", moduleAnnotation.id(), "enabled").getBoolean(false)) {
                Carbon.getCarbon().getLogger()
                        .info("Loading module: " + moduleAnnotation.name() + " (" + moduleAnnotation.id() + ")");

                Injector injector = Guice.createInjector(new ModuleGuiceModule(moduleAnnotation));
                Object instance = injector.getInstance(module);

                Carbon.getCarbon().getEventBus().register(instance);
                if (container != null) {
                    this.modules.put(moduleAnnotation.id(), ModuleContainer.of(moduleAnnotation, instance, container));
                } else {
                    this.modules.put(moduleAnnotation.id(), ModuleContainer.of(moduleAnnotation, instance));
                }

                Carbon.getCarbon().getLogger()
                        .info("Loaded module: " + moduleAnnotation.name() + " (" + moduleAnnotation.id() + ")");
            }
        } else {
            Carbon.getCarbon().getLogger().error(module.getName() + " has no @Module annotation!");
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
