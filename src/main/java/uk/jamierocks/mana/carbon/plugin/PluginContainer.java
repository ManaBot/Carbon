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
     * @since 1.0.0
     */
    static PluginContainer of(Plugin plugin, Object instance) {
        checkNotNull(plugin, "plugin is null!");
        checkNotNull(instance, "instance is null!");

        return PluginContainer.of(plugin.id(), plugin.name(), plugin.version(), instance);
    }

    /**
     * Creates a {@link PluginContainer} with the given values.
     *
     * @param id The identifier
     * @param name The name
     * @param version The version
     * @param instance The plugin instance
     * @return The plugin container
     * @since 1.0.0
     */
    static PluginContainer of(String id, String name, String version, Object instance) {
        checkNotNull(id, "id is null!");
        checkNotNull(name, "name is null!");
        checkNotNull(version, "version is null!");
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
     * @since 1.0.0
     */
    Object getInstance();
}
