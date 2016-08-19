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

import uk.jamierocks.mana.carbon.plugin.PluginContainer;

import java.util.Collection;
import java.util.Optional;

/**
 * A manager for registering modules.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface ModuleManager {

    /**
     * Registers the given module, to the given plugin.
     *
     * @param plugin The plugin instance or container
     * @param module The module
     * @since 1.1.0
     */
    void registerModule(Object plugin, Class<?> module);

    /**
     * Returns the owner of the given module, if available.
     *
     * @param moduleClass The module
     * @return The plugin owner
     * @since 1.1.0
     */
    default Optional<PluginContainer> getOwner(Class<?> moduleClass) {
        checkNotNull(moduleClass, "moduleClass is null!");

        final Optional<ModuleContainer> module = this.getModule(moduleClass);
        if (module.isPresent()) {
            return Optional.of(module.get().getOwner());
        }
        return Optional.empty();
    }

    /**
     * Returns the owner of the given module, if available.
     *
     * @param id The module id
     * @return the plugin owner
     * @since 1.1.0
     */
    default Optional<PluginContainer> getOwner(String id) {
        checkNotNull(id, "id is null!");

        final Optional<ModuleContainer> module = this.getModule(id);
        if (module.isPresent()) {
            return Optional.of(module.get().getOwner());
        }
        return Optional.empty();
    }

    /**
     * Returns the {@link ModuleContainer} for the module requested, if available.
     *
     * @param module The module class
     * @return The module container
     * @since 1.1.0
     */
    default Optional<ModuleContainer> getModule(Class<?> module) {
        checkNotNull(module, "module is null!");

        if (module.isAnnotationPresent(Module.class)) {
            final Module moduleAnnotation = module.getDeclaredAnnotation(Module.class);
            return this.getModule(moduleAnnotation.id());
        }
        return Optional.empty();
    }

    /**
     * Returns the {@link ModuleContainer} for the module requested, if available.
     *
     * @param id The module id
     * @return The module container
     * @since 1.1.0
     */
    Optional<ModuleContainer> getModule(String id);

    /**
     * Gets an immutable collection of the loaded {@link Module}s.
     *
     * @return The list of modules
     * @since 1.1.0
     */
    Collection<ModuleContainer> getModules();
}
