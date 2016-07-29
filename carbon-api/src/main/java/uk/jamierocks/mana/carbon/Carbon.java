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
import uk.jamierocks.mana.carbon.irc.IRCManager;
import uk.jamierocks.mana.carbon.module.ModuleManager;
import uk.jamierocks.mana.carbon.plugin.PluginContainer;
import uk.jamierocks.mana.carbon.plugin.PluginManager;
import uk.jamierocks.mana.carbon.service.ServiceRegistry;

/**
 * Holds all the necessary components for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public abstract class Carbon {

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
        checkNotNull(CONTAINER, "CONTAINER is null!");
        return (Carbon) CONTAINER.getInstance().get();
    }

    /**
     * Gets the {@link EventBus} used by Carbon.
     *
     * @return Carbon's event bus
     * @since 1.0.0
     */
    public abstract EventBus getEventBus();

    /**
     * Gets the {@link PluginManager} used by Carbon.
     *
     * @return Carbon's plugin manager
     * @since 1.0.0
     */
    public abstract PluginManager getPluginManager();

    /**
     * Gets the {@link ModuleManager} used by Carbon.
     *
     * @return Carbon's module manager
     * @since 1.0.0
     */
    public abstract ModuleManager getModuleManager();

    /**
     * Gets the {@link IRCManager} used by Carbon.
     *
     * @return Carbon's irc manager
     * @since 1.0.0
     */
    public abstract IRCManager getIRCManager();

    /**
     * Gets the {@link ServiceRegistry} used by Carbon.
     *
     * @return Carbon's service registry
     * @since 1.0.0
     */
    public abstract ServiceRegistry getServiceRegistry();

    /**
     * Gets the {@link Dispatcher} used by Carbon.
     *
     * @return Carbon's command dispatcher
     * @since 1.0.0
     */
    public abstract Dispatcher getCommandDispatcher();

    /**
     * Gets the {@link CarbonConfiguration} used by Carbon.
     *
     * @return Carbon's configuration
     * @since 2.0.0
     */
    public abstract CarbonConfiguration getConfiguration();
}
