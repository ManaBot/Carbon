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

import java.util.List;
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
     * Gets an immutable {@link List} of the loaded {@link Plugin}s.
     *
     * @return The list of plugins
     * @since 1.0.0
     */
    List<PluginContainer> getPlugins();

    /**
     * Checks if a plugin with the given identifier is currently loaded.
     *
     * @param id The plugin's id
     * @return {@code True} if the plugin is loaded, {@code false} otherwise.
     * @since 1.0.0
     */
    boolean isLoaded(String id);
}
