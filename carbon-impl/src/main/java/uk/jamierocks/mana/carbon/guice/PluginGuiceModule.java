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

package uk.jamierocks.mana.carbon.guice;

import com.google.inject.AbstractModule;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.jamierocks.mana.carbon.Carbon;
import uk.jamierocks.mana.carbon.irc.IRCManager;
import uk.jamierocks.mana.carbon.module.ModuleManager;
import uk.jamierocks.mana.carbon.plugin.Plugin;
import uk.jamierocks.mana.carbon.plugin.PluginManager;
import uk.jamierocks.mana.carbon.service.ServiceRegistry;

/**
 * The Guice module for {@link Plugin}s within Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class PluginGuiceModule extends AbstractModule {

    private final Plugin plugin;

    /**
     * Constructs a new Guice module for a {@link Plugin}.
     *
     * @param plugin The plugin
     */
    public PluginGuiceModule(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        // The basics
        this.bind(Carbon.class).toInstance(Carbon.getCarbon());
        this.bind(Logger.class).toInstance(LoggerFactory.getLogger(this.plugin.name()));

        // The managers
        this.bind(PluginManager.class).toInstance(Carbon.getCarbon().getPluginManager());
        this.bind(ModuleManager.class).toInstance(Carbon.getCarbon().getModuleManager());
        this.bind(IRCManager.class).toInstance(Carbon.getCarbon().getIRCManager());
        this.bind(ServiceRegistry.class).toInstance(Carbon.getCarbon().getServiceRegistry());

        // Config node
        this.bind(CommentedConfigurationNode.class)
                .toInstance(Carbon.getCarbon().getConfiguration().getNode().getNode("plugin", this.plugin.id()));
    }
}