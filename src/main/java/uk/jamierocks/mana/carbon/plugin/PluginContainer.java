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

package uk.jamierocks.mana.carbon.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A wrapper around a plugin.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface PluginContainer {

    /**
     * Creates a {@link PluginContainer} around a {@link Plugin} and an {@link Object}.
     *
     * @param plugin The plugin annotation
     * @param instance The plugin instance
     * @return The plugin container
     */
    static PluginContainer of(Plugin plugin, Object instance) {
        checkNotNull(plugin, "@Plugin annotation is null!");
        checkNotNull(instance, "Plugin instance is null!");

        return new PluginContainer() {
            @Override
            public String getId() {
                return plugin.id();
            }

            @Override
            public String getName() {
                return plugin.name();
            }

            @Override
            public String getVersion() {
                return plugin.version();
            }

            @Override
            public Object getInstance() {
                return instance;
            }
        };
    }

    /**
     * Gets the identifier of the plugin.
     *
     * @return The identifier
     * @see Plugin#id()
     * @since 1.0.0
     */
    String getId();

    /**
     * Gets the name of the plugin.
     *
     * @return The name
     * @see Plugin#name()
     * @since 1.0.0
     */
    String getName();

    /**
     * Gets the version of the plugin.
     *
     * @return The version
     * @see Plugin#version()
     * @since 1.0.0
     */
    String getVersion();

    /**
     * Gets the instance of this plugin.
     *
     * @return The instance
     */
    Object getInstance();
}
