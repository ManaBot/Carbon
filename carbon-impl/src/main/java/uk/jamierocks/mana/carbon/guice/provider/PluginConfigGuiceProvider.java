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

package uk.jamierocks.mana.carbon.guice.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import uk.jamierocks.mana.carbon.plugin.PluginContainer;

/**
 * The Guice {@link Provider} for the plugin {@link CommentedConfigurationNode}.
 *
 * @author Jamie Mansfield
 * @since 2.0.0
 */
public final class PluginConfigGuiceProvider implements Provider<CommentedConfigurationNode> {

    private final PluginContainer container;

    @Inject
    public PluginConfigGuiceProvider(PluginContainer container) {
        this.container = container;
    }

    @Override
    public CommentedConfigurationNode get() {
        return this.container.getConfiguration();
    }
}
