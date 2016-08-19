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
        return this.pluginManager.getInstance(this);
    }
}
