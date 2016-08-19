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
