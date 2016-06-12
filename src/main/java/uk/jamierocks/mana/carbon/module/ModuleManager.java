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

import uk.jamierocks.mana.carbon.plugin.PluginContainer;

import java.util.Optional;

/**
 * A manager for registering modules.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface ModuleManager {

    /**
     * Registers the given module, to an uncredited plugin.
     *
     * @param module The module
     * @since 1.0.0
     * @deprecated As of release 1.1.0, replaced by {@link #registerModule(Object, Class)}
     */
    @Deprecated
    void registerModule(Class<?> module);

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
     * @param module The module
     * @return The plugin owner
     * @since 1.1.0
     */
    Optional<PluginContainer> getOwner(Class<?> module);

    /**
     * Returns the {@link ModuleContainer} for the module requested, if available.
     *
     * @param module The module class
     * @return The module container
     * @since 1.1.0
     */
    default Optional<ModuleContainer> getModule(Class<?> module) {
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
}
