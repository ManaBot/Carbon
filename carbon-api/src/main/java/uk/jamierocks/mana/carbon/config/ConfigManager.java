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

package uk.jamierocks.mana.carbon.config;

import static com.google.common.base.Preconditions.checkNotNull;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

/**
 * A manager for the configuration.
 *
 * @author Jamie Mansfield
 * @since 2.0.0
 */
public class ConfigManager {

    private final CommentedConfigurationNode configurationNode;

    public ConfigManager(CommentedConfigurationNode configurationNode) {
        this.configurationNode = configurationNode;
    }

    /**
     * Gets the configuration node.
     *
     * @return The config node
     * @since 2.0.0
     */
    public CommentedConfigurationNode getConfigurationNode() {
        return this.configurationNode;
    }

    /**
     * Gets the value from the given key.
     *
     * @param key The key
     * @param <T> The type of the value
     * @return The value
     * @since 2.0.0
     */
    public <T> T get(ConfigKey<T> key) {
        checkNotNull(key, "key is null!");

        T value = (T) this.configurationNode.getNode((Object[]) key.getPath()).getValue();
        checkNotNull(value, "Cannot retrieve non-existent config key");

        return value;
    }
}
