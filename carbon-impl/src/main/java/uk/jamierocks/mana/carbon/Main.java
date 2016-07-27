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

import uk.jamierocks.mana.carbon.event.state.InitialisationEvent;
import uk.jamierocks.mana.carbon.event.state.PostInitialisationEvent;
import uk.jamierocks.mana.carbon.event.state.PreInitialisationEvent;
import uk.jamierocks.mana.carbon.irc.CarbonIRCManager;
import uk.jamierocks.mana.carbon.modules.HelpModule;
import uk.jamierocks.mana.carbon.modules.InviteModule;
import uk.jamierocks.mana.carbon.plugin.CarbonPluginManager;
import uk.jamierocks.mana.carbon.service.exception.ExceptionService;
import uk.jamierocks.mana.carbon.service.exception.SimpleExceptionService;
import uk.jamierocks.mana.carbon.util.command.CommandListener;

/**
 * The application entry-point for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class Main {

    public static void main(String[] args) {
        // Initialise Carbon
        new CarbonImpl();

        // Load all of the plugins
        ((CarbonPluginManager) Carbon.getCarbon().getPluginManager()).registerPlugin(Carbon.CONTAINER);
        ((CarbonPluginManager) Carbon.getCarbon().getPluginManager()).loadAllPlugins();

        // Pre Init state
        new PreInitialisationEvent().post();

        // Register exception service.
        Carbon.getCarbon().getServiceRegistry()
                .registerProvider(Carbon.getCarbon(), ExceptionService.class, new SimpleExceptionService());

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
