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
import uk.jamierocks.mana.carbon.service.exception.ExceptionReporter;
import uk.jamierocks.mana.carbon.util.Constants;
import uk.jamierocks.mana.carbon.util.ReflectionUtil;
import uk.jamierocks.mana.carbon.util.ReflectionUtilException;
import uk.jamierocks.mana.carbon.util.event.ExceptionReporterEventLoggingHandler;

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

    /**
     * This will be forcefully overridden by Carbon upon its initialisation.
     */
    protected static final PluginContainer CONTAINER = null;
    private static final Path CONFIG_PATH = Paths.get("carbon.conf");
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
        this.eventBus = new EventBus(new ExceptionReporterEventLoggingHandler());
        this.pluginManager = new CarbonPluginManager();
        this.moduleManager = new CarbonModuleManager();
        this.ircManager = new CarbonIRCManager();
        this.serviceRegistry = new CarbonServiceRegistry();
        this.commandDispatcher = new SimpleDispatcher();

        if (Files.notExists(CONFIG_PATH)) {
            try {
                Files.copy(Carbon.class.getResourceAsStream("/carbon.conf"), CONFIG_PATH);
            } catch (IOException e) {
                // If this ever occurs something massively wrong is going on.
                // It is probably for the best to exit the application
                ExceptionReporter.report("Carbon has experienced a fatal error! Exiting!", e);
                System.exit(0);
            }
        }

        try {
            this.configurationNode = HoconConfigurationLoader.builder().setPath(CONFIG_PATH).build().load();
        } catch (IOException e) {
            // If this ever occurs something massively wrong is going on.
            // It is probably for the best to exit the application
            ExceptionReporter.report("Carbon has experienced a fatal error! Exiting!", e);
            System.exit(0);
        }

        this.setContainer(); // Forcefully sets the container
        this.setCommandPrefix(); // Forcefully set the command prefix
    }

    /**
     * Gets the instance of {@link Carbon} currently running.
     *
     * @return The current instance of Carbon
     * @since 1.0.0
     */
    public static Carbon getCarbon() {
        checkNotNull(CONTAINER, "Carbon has not yet been initialised!");
        return (Carbon) CONTAINER.getInstance();
    }

    private void setContainer() {
        try {
            ReflectionUtil.setStaticFinal(this.getClass(), "CONTAINER",
                    PluginContainer.of("carbon", "Carbon", Constants.VERSION, this));
        } catch (ReflectionUtilException e) {
            // If this ever occurs something massively wrong is going on.
            // It is probably for the best to exit the application
            ExceptionReporter.report("Carbon has experienced a fatal error! Exiting!", e);
            System.exit(0);
        }
    }

    private void setCommandPrefix() {
        try {
            final String prefix = this.configurationNode.getNode("commands", "prefix").getString();
            this.logger.info("Using command prefix: " + prefix);
            ReflectionUtil.setStaticFinal(Constants.class, "COMMAND_PREFIX", prefix);
        } catch (ReflectionUtilException e) {
            ExceptionReporter.report("Failed to set the command prefix!", e);
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
