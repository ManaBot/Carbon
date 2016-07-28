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

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

/**
 * A wrapper around a plugin.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface PluginContainer {

    /**
     * Creates a {@link PluginContainer} around a {@link Plugin}, a {@link CommentedConfigurationNode} and an {@link Object}.
     *
     * @param plugin The plugin annotation
     * @param instance The plugin instance
     * @param node the configuration node
     * @return The plugin container
     * @since 2.0.0
     */
    static PluginContainer of(Plugin plugin, CommentedConfigurationNode node, Object instance) {
        checkNotNull(plugin, "plugin is null!");
        checkNotNull(node, "node is null!");
        checkNotNull(instance, "instance is null!");

        return PluginContainer.of(plugin.id(), plugin.name(), plugin.version(), node, instance);
    }

    /**
     * Creates a {@link PluginContainer} with the given values.
     *
     * @param id The identifier
     * @param name The name
     * @param version The version
     * @param node The configuration node
     * @param instance The plugin instance
     * @return The plugin container
     * @since 2.0.0
     */
    static PluginContainer of(String id, String name, String version, CommentedConfigurationNode node, Object instance) {
        checkNotNull(id, "id is null!");
        checkNotNull(name, "name is null!");
        checkNotNull(version, "version is null!");
        checkNotNull(node, "node is null!");
        checkNotNull(instance, "instance is null!");

        return new PluginContainer() {
            @Override
            public String getId() {
                return id;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getVersion() {
                return version;
            }

            @Override
            public CommentedConfigurationNode getConfiguration() {
                return node;
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
     * Gets the configuration node of the plugin.
     *
     * @return The configuration node
     * @since 2.0.0
     */
    CommentedConfigurationNode getConfiguration();

    /**
     * Gets the instance of this plugin.
     *
     * @return The instance
     * @since 1.0.0
     */
    Object getInstance();
}
