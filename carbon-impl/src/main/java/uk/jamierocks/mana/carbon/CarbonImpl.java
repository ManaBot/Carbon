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

import static uk.jamierocks.mana.carbon.util.Constants.CONFIG_PATH;
import static uk.jamierocks.mana.carbon.util.Constants.OPS_PATH;

import com.google.common.eventbus.EventBus;
import com.sk89q.intake.dispatcher.Dispatcher;
import com.sk89q.intake.dispatcher.SimpleDispatcher;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.jamierocks.mana.carbon.config.CarbonConfigManager;
import uk.jamierocks.mana.carbon.irc.CarbonIRCManager;
import uk.jamierocks.mana.carbon.irc.IRCManager;
import uk.jamierocks.mana.carbon.module.CarbonModuleManager;
import uk.jamierocks.mana.carbon.module.ModuleManager;
import uk.jamierocks.mana.carbon.plugin.CarbonPluginManager;
import uk.jamierocks.mana.carbon.plugin.PluginContainer;
import uk.jamierocks.mana.carbon.plugin.PluginManager;
import uk.jamierocks.mana.carbon.service.CarbonServiceRegistry;
import uk.jamierocks.mana.carbon.service.ServiceRegistry;
import uk.jamierocks.mana.carbon.service.exception.ExceptionReporter;
import uk.jamierocks.mana.carbon.util.Constants;
import uk.jamierocks.mana.carbon.util.ReflectionUtil;
import uk.jamierocks.mana.carbon.util.ReflectionUtilException;
import uk.jamierocks.mana.carbon.util.event.ExceptionReporterEventLoggingHandler;

import java.io.IOException;
import java.nio.file.Files;

/**
 * The implementation of {@link Carbon}.
 *
 * @author Jamie Mansfield
 * @since 2.0.0
 */
public final class CarbonImpl extends Carbon {

    public static final Logger LOGGER = LoggerFactory.getLogger("Carbon");

    private final EventBus eventBus;
    private final PluginManager pluginManager;
    private final ModuleManager moduleManager;
    private final IRCManager ircManager;
    private final ServiceRegistry serviceRegistry;
    private final Dispatcher commandDispatcher;
    private CarbonConfiguration configuration;

    protected CarbonImpl() {
        LOGGER.info("Loading Carbon " + Constants.VERSION);
        this.eventBus = new EventBus(new ExceptionReporterEventLoggingHandler());
        this.pluginManager = new CarbonPluginManager();
        this.moduleManager = new CarbonModuleManager();
        this.ircManager = new CarbonIRCManager();
        this.serviceRegistry = new CarbonServiceRegistry();
        this.commandDispatcher = new SimpleDispatcher();

        // Configuration-related stuff
        this.configuration = CarbonConfigManager.getCarbonConfig();
        CarbonConfigManager.checkOpsFile();
        LOGGER.info("Using command prefix: " + this.configuration.getCommands().getPrefix());

        // Forcefully sets the container
        this.setContainer();
    }

    private void setContainer() {
        try {
            ReflectionUtil.setStaticFinal(Carbon.class, "CONTAINER",
                    PluginContainer.of("carbon", "Carbon", Constants.VERSION, this.configuration.getNode(), this));
        } catch (ReflectionUtilException e) {
            // If this ever occurs something massively wrong is going on.
            // It is probably for the best to exit the application
            ExceptionReporter.report("Carbon has experienced a fatal error! Exiting!", e);
            System.exit(0);
        }
    }

    @Override
    public EventBus getEventBus() {
        return this.eventBus;
    }

    @Override
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    @Override
    public IRCManager getIRCManager() {
        return this.ircManager;
    }

    @Override
    public ServiceRegistry getServiceRegistry() {
        return this.serviceRegistry;
    }

    @Override
    public Dispatcher getCommandDispatcher() {
        return this.commandDispatcher;
    }

    @Override
    public CarbonConfiguration getConfiguration() {
        return this.configuration;
    }
}
