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

/**
 * A wrapper around a module.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface ModuleContainer {

    /**
     * Creates a {@link ModuleContainer} around a {@link Module}, a {@link Object} and a {@link PluginContainer}.
     *
     * @param module The module annotation
     * @param instance The module instance
     * @param owner The owner of the module
     * @return The module container
     * @since 1.1.0
     */
    static ModuleContainer of(Module module, Object instance, PluginContainer owner) {
        checkNotNull(module, "module is null!");
        checkNotNull(instance, "instance is null!");
        checkNotNull(owner, "owner is null!");

        return new ModuleContainer() {
            @Override
            public String getId() {
                return module.id();
            }

            @Override
            public String getName() {
                return module.name();
            }

            @Override
            public PluginContainer getOwner() {
                return owner;
            }

            @Override
            public Object getInstance() {
                return instance;
            }
        };
    }

    /**
     * Gets the identifier of the module.
     *
     * @return The identifier
     * @see Module#id()
     * @since 1.0.0
     */
    String getId();

    /**
     * Gets the name of the module.
     *
     * @return The name
     * @see Module#name()
     * @since 1.0.0
     */
    String getName();

    /**
     * Gets the owner of the module.
     *
     * @return The owner
     * @since 2.0.0
     */
    PluginContainer getOwner();

    /**
     * Gets the instance of this module.
     *
     * @return The instance
     * @since 1.0.0
     */
    Object getInstance();
}
