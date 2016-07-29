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

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

import java.util.Optional;

/**
 * The implementation of {@link PluginContainer}.
 *
 * @author Jamie Mansfield
 * @since 2.0.0
 */
public class CarbonPluginContainer implements PluginContainer {

    private final CarbonPluginManager pluginManager;
    private final Plugin plugin;
    private final CommentedConfigurationNode node;

    public CarbonPluginContainer(CarbonPluginManager pluginManager, Plugin plugin, CommentedConfigurationNode node) {
        this.pluginManager = pluginManager;
        this.plugin = plugin;
        this.node = node;
    }

    @Override
    public String getId() {
        return this.plugin.id();
    }

    @Override
    public String getName() {
        return this.plugin.name();
    }

    @Override
    public String getVersion() {
        return this.plugin.version();
    }

    @Override
    public CommentedConfigurationNode getConfiguration() {
        return this.node;
    }

    @Override
    public Optional<Object> getInstance() {
        if (this.pluginManager.containerToInstance.containsKey(this)) {
            return Optional.ofNullable(this.pluginManager.containerToInstance.get(this));
        }
        return Optional.empty();
    }
}
