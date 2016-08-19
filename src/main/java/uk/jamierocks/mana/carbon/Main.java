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

import uk.jamierocks.mana.carbon.event.state.InitialisationEvent;
import uk.jamierocks.mana.carbon.event.state.PostInitialisationEvent;
import uk.jamierocks.mana.carbon.event.state.PreInitialisationEvent;
import uk.jamierocks.mana.carbon.service.exception.ExceptionService;
import uk.jamierocks.mana.carbon.service.exception.SimpleExceptionService;
import uk.jamierocks.mana.carbon.util.command.CommandListener;
import uk.jamierocks.mana.carbon.util.extra.HelpModule;
import uk.jamierocks.mana.carbon.util.extra.InviteModule;

/**
 * The application entry-point for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class Main {

    public static void main(String[] args) {
        // Initialise Carbon
        new Carbon();

        // Load all of the plugins
        ((CarbonPluginManager) Carbon.getCarbon().getPluginManager()).loadPlugin(Carbon.CONTAINER);
        ((CarbonPluginManager) Carbon.getCarbon().getPluginManager()).loadAllPlugins();

        // Pre Init state
        new PreInitialisationEvent().post();

        // Register exception service.
        Carbon.getCarbon().getServiceRegistry()
                .registerProvider(Carbon.getCarbon(), ExceptionService.class, new SimpleExceptionService());

        // Initialise IRC
        ((CarbonIRCManager) Carbon.getCarbon().getIRCManager()).initialise();

        // Register command listener
        Carbon.getCarbon().getIRCManager().registerIRCEventListener(new CommandListener());

        // Init state
        new InitialisationEvent().post();

        // Register builtin modules
        Carbon.getCarbon().getModuleManager().registerModule(Carbon.getCarbon(), InviteModule.class);
        Carbon.getCarbon().getModuleManager().registerModule(Carbon.getCarbon(), HelpModule.class);

        // Post Init state
        new PostInitialisationEvent().post();
    }
}
