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

import java.util.Collection;
import java.util.Optional;

/**
 * A manager for Carbon plugins.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface PluginManager {

    /**
     * Gets a {@link PluginContainer} from an instance, if available.
     *
     * @param instance The plugin instance
     * @return The plugin container
     * @since 1.0.0
     */
    Optional<PluginContainer> fromInstance(Object instance);

    /**
     * Gets the {@link PluginContainer} from the given identifier.
     *
     * @param id The plugin's id
     * @return The plugin container
     * @since 1.0.0
     */
    Optional<PluginContainer> getPlugin(String id);

    /**
     * Gets an immutable collection of the loaded {@link Plugin}s.
     *
     * @return The list of plugins
     * @since 1.0.0
     */
    Collection<PluginContainer> getPlugins();

    /**
     * Checks if a plugin with the given identifier is currently loaded.
     *
     * @param id The plugin's id
     * @return {@code True} if the plugin is loaded, {@code false} otherwise.
     * @since 1.0.0
     */
    boolean isLoaded(String id);
}
