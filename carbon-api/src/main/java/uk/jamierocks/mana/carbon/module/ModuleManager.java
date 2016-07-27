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
