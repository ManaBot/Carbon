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

import static uk.jamierocks.mana.carbon.util.Constants.CONFIG_PATH;

import com.google.common.eventbus.EventBus;
import com.sk89q.intake.dispatcher.Dispatcher;
import com.sk89q.intake.dispatcher.SimpleDispatcher;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import uk.jamierocks.mana.carbon.util.CommandUtils;
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

    private final Logger logger = LoggerFactory.getLogger("Carbon");
    private final EventBus eventBus;
    private final PluginManager pluginManager;
    private final ModuleManager moduleManager;
    private final IRCManager ircManager;
    private final ServiceRegistry serviceRegistry;
    private final Dispatcher commandDispatcher;
    private CarbonConfiguration configuration;

    protected CarbonImpl() {
        this.logger.info("Loading Carbon " + Constants.VERSION);
        this.eventBus = new EventBus(new ExceptionReporterEventLoggingHandler());
        this.pluginManager = new CarbonPluginManager();
        this.moduleManager = new CarbonModuleManager();
        this.ircManager = new CarbonIRCManager();
        this.serviceRegistry = new CarbonServiceRegistry();
        this.commandDispatcher = new SimpleDispatcher();

        this.setContainer(); // Forcefully sets the container

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
            this.configuration = new CarbonConfiguration(HoconConfigurationLoader.builder().setPath(CONFIG_PATH).build().load());
        } catch (IOException e) {
            // If this ever occurs something massively wrong is going on.
            // It is probably for the best to exit the application
            ExceptionReporter.report("Carbon has experienced a fatal error! Exiting!", e);
            System.exit(0);
        }

        this.logger.info("Using command prefix: " + CommandUtils.getCommandPrefix());
    }

    private void setContainer() {
        try {
            ReflectionUtil.setStaticFinal(Carbon.class, "CONTAINER", PluginContainer.of("carbon", "Carbon", Constants.VERSION, this));
        } catch (ReflectionUtilException e) {
            // If this ever occurs something massively wrong is going on.
            // It is probably for the best to exit the application
            ExceptionReporter.report("Carbon has experienced a fatal error! Exiting!", e);
            System.exit(0);
        }
    }

    @Override
    public Logger getLogger() {
        return this.logger;
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
