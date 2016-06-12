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

package uk.jamierocks.mana.carbon;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.eventbus.EventBus;
import com.sk89q.intake.dispatcher.Dispatcher;
import com.sk89q.intake.dispatcher.SimpleDispatcher;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.jamierocks.mana.carbon.irc.IRCManager;
import uk.jamierocks.mana.carbon.module.ModuleManager;
import uk.jamierocks.mana.carbon.plugin.PluginContainer;
import uk.jamierocks.mana.carbon.plugin.PluginManager;
import uk.jamierocks.mana.carbon.service.ServiceRegistry;
import uk.jamierocks.mana.carbon.util.Constants;
import uk.jamierocks.mana.carbon.util.ReflectionUtil;
import uk.jamierocks.mana.carbon.util.ReflectionUtilException;
import uk.jamierocks.mana.carbon.util.event.Slf4jEventLoggingHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Holds all the necessary components for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class Carbon {

    private static final Path CONFIG_PATH = Paths.get("carbon.conf");

    /**
     * This will be forcefully overridden by Carbon upon its initialisation.
     */
    private static final Carbon INSTANCE = null;

    /**
     * This will be forcefully overridden by Carbon upon its initialisation.
     */
    protected static final PluginContainer CONTAINER = null;

    /**
     * Gets the instance of {@link Carbon} currently running.
     *
     * @return The current instance of Carbon
     * @since 1.0.0
     */
    public static Carbon getCarbon() {
        checkNotNull(INSTANCE, "INSTANCE is null!");
        return INSTANCE;
    }

    private final Logger logger = LoggerFactory.getLogger("Carbon");
    private final EventBus eventBus;
    private final PluginManager pluginManager;
    private final ModuleManager moduleManager;
    private final IRCManager ircManager;
    private final ServiceRegistry serviceRegistry;
    private final Dispatcher commandDispatcher;
    private CommentedConfigurationNode configurationNode;

    protected Carbon() {
        this.logger.info("Loading Carbon " + Constants.VERSION);
        this.eventBus = new EventBus(new Slf4jEventLoggingHandler());
        this.pluginManager = new CarbonPluginManager();
        this.moduleManager = new CarbonModuleManager();
        this.ircManager = new CarbonIRCManager();
        this.serviceRegistry = new CarbonServiceRegistry();
        this.commandDispatcher = new SimpleDispatcher();

        this.setInstance(); // Forcefully sets the instance
        this.setContainer(); // Forcefully sets the container

        if (Files.notExists(CONFIG_PATH)) {
            try {
                Files.copy(Carbon.class.getResourceAsStream("/carbon.conf"), CONFIG_PATH);
            } catch (IOException e) {
                // If this ever occurs something massively wrong is going on.
                // It is probably for the best to exit the application
                this.getLogger().error("Carbon has experienced a fatal error! Exiting!", e);
                System.exit(0);
            }
        }

        try {
            this.configurationNode = HoconConfigurationLoader.builder().setPath(CONFIG_PATH).build().load();
        } catch (IOException e) {
            // If this ever occurs something massively wrong is going on.
            // It is probably for the best to exit the application
            this.getLogger().error("Carbon has experienced a fatal error! Exiting!", e);
            System.exit(0);
        }

        this.setCommandPrefix(); // Forcefully set the command prefix
    }

    private void setInstance() {
        try {
            ReflectionUtil.setStaticFinal(this.getClass(), "INSTANCE", this);
        } catch (ReflectionUtilException e) {
            // If this ever occurs something massively wrong is going on.
            // It is probably for the best to exit the application
            this.getLogger().error("Carbon has experienced a fatal error! Exiting!", e);
            System.exit(0);
        }
    }

    private void setContainer() {
        try {
            ReflectionUtil.setStaticFinal(this.getClass(), "CONTAINER",
                    PluginContainer.of("carbon", "Carbon", Constants.VERSION, this));
        } catch (ReflectionUtilException e) {
            // If this ever occurs something massively wrong is going on.
            // It is probably for the best to exit the application
            this.getLogger().error("Carbon has experienced a fatal error! Exiting!", e);
            System.exit(0);
        }
    }

    private void setCommandPrefix() {
        try {
            final String prefix = this.configurationNode.getNode("commands", "prefix").getString();
            this.logger.info("Using command prefix: " + prefix);
            ReflectionUtil.setStaticFinal(Constants.class, "COMMAND_PREFIX", prefix);
        } catch (ReflectionUtilException e) {
            this.getLogger().error("Failed to set the command prefix!", e);
        }
    }

    /**
     * Gets the {@link Logger} used by Carbon.
     *
     * <b>It is designed for use internally, and it is recommended
     * that plugins do NOT use this Logger.</b>
     *
     * @return Carbon's logger
     * @since 1.0.0
     */
    public Logger getLogger() {
        return this.logger;
    }

    /**
     * Gets the {@link EventBus} used by Carbon.
     *
     * @return Carbon's event bus
     * @since 1.0.0
     */
    public EventBus getEventBus() {
        return this.eventBus;
    }

    /**
     * Gets the {@link PluginManager} used by Carbon.
     *
     * @return Carbon's plugin manager
     * @since 1.0.0
     */
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    /**
     * Gets the {@link ModuleManager} used by Carbon.
     *
     * @return Carbon's module manager
     * @since 1.0.0
     */
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    /**
     * Gets the {@link IRCManager} used by Carbon.
     *
     * @return Carbon's irc manager
     * @since 1.0.0
     */
    public IRCManager getIRCManager() {
        return this.ircManager;
    }

    /**
     * Gets the {@link ServiceRegistry} used by Carbon.
     *
     * @return Carbon's service registry
     * @since 1.0.0
     */
    public ServiceRegistry getServiceRegistry() {
        return this.serviceRegistry;
    }

    /**
     * Gets the {@link Dispatcher} used by Carbon.
     *
     * @return Carbon's command dispatcher
     * @since 1.0.0
     */
    public Dispatcher getCommandDispatcher() {
        return this.commandDispatcher;
    }

    /**
     * Gets the {@link CommentedConfigurationNode} used by Carbon.
     *
     * @return Carbon's configuration node
     * @since 1.0.0
     */
    public CommentedConfigurationNode getConfigurationNode() {
        return this.configurationNode;
    }
}
