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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.jamierocks.mana.carbon.plugin.PluginManager;
import uk.jamierocks.mana.carbon.service.ServiceRegistry;
import uk.jamierocks.mana.carbon.util.event.Slf4jEventLoggingHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
    private static final Carbon INSTANCE = null;

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

    private final Logger logger;
    private final EventBus eventBus;
    private final PluginManager pluginManager;
    private final ServiceRegistry serviceRegistry;

    protected Carbon() {
        this.logger = LoggerFactory.getLogger("Carbon");
        this.eventBus = new EventBus(new Slf4jEventLoggingHandler());
        this.pluginManager = new PluginManager();
        this.serviceRegistry = new CarbonServiceRegistry();

        this.setInstance(); // Forcefully sets the instance
    }

    private void setInstance() {
        try {
            Field instanceField = Carbon.class.getDeclaredField("INSTANCE");
            instanceField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(instanceField, instanceField.getModifiers() & ~Modifier.FINAL);

            instanceField.set(null, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // If this ever occurs something massively wrong is going on.
            // It is probably for the best to exit the application
            this.getLogger().error("Carbon has experienced a fatal error! Exiting!", e);
            System.exit(0);
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
     * Gets the {@link ServiceRegistry} used by Carbon.
     *
     * @return Carbon's service registry
     * @since 1.0.0
     */
    public ServiceRegistry getServiceRegistry() {
        return this.serviceRegistry;
    }
}
