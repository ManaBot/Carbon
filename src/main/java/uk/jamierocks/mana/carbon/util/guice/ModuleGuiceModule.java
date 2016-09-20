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

package uk.jamierocks.mana.carbon.util.guice;

import com.google.inject.AbstractModule;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.jamierocks.mana.carbon.Carbon;
import uk.jamierocks.mana.carbon.irc.IRCManager;
import uk.jamierocks.mana.carbon.module.Module;
import uk.jamierocks.mana.carbon.module.ModuleManager;
import uk.jamierocks.mana.carbon.plugin.PluginManager;
import uk.jamierocks.mana.carbon.service.ServiceRegistry;

/**
 * The Guice module for {@link Module}s within Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class ModuleGuiceModule extends AbstractModule {

    private final Module module;

    /**
     * Constructs a new Guice module for a {@link Module}.
     *
     * @param module The module
     */
    public ModuleGuiceModule(Module module) {
        this.module = module;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        // The basics
        this.bind(Carbon.class).toInstance(Carbon.getCarbon());
        this.bind(Logger.class).toInstance(LoggerFactory.getLogger(this.module.name()));

        // The managers
        this.bind(PluginManager.class).toInstance(Carbon.getCarbon().getPluginManager());
        this.bind(ModuleManager.class).toInstance(Carbon.getCarbon().getModuleManager());
        this.bind(IRCManager.class).toInstance(Carbon.getCarbon().getIRCManager());
        this.bind(ServiceRegistry.class).toInstance(Carbon.getCarbon().getServiceRegistry());

        // Config node
        this.bind(CommentedConfigurationNode.class)
                .toInstance(Carbon.getCarbon().getConfigurationNode().getNode("module", this.module.id()));
    }
}
