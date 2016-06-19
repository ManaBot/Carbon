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

package uk.jamierocks.mana.carbon.util.extra;

import static uk.jamierocks.mana.carbon.Carbon.getCarbon;

import com.google.common.eventbus.Subscribe;
import org.kitteh.irc.client.library.event.channel.ChannelInviteEvent;
import org.kitteh.irc.lib.net.engio.mbassy.listener.Handler;
import uk.jamierocks.mana.carbon.event.state.PostInitialisationEvent;
import uk.jamierocks.mana.carbon.module.Module;
import uk.jamierocks.mana.carbon.util.extra.command.JoinCommand;
import uk.jamierocks.mana.carbon.util.extra.command.PartCommand;

/**
 * A invite module for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
@Module(id = "invite", name = "Invite")
public final class InviteModule {

    @Subscribe
    public void onPostInitialisation(PostInitialisationEvent event) {
        // Register listener
        event.getCarbon().getIRCManager().getClients().forEach(c -> c.getEventManager().registerEventListener(this));

        // Register commands
        event.getCarbon().getCommandDispatcher().registerCommand(new JoinCommand(), "join");
        event.getCarbon().getCommandDispatcher().registerCommand(new PartCommand(), "part", "quit", "leave");
    }

    @Handler
    public void onInvite(ChannelInviteEvent event) {
        if (event.getTarget().equalsIgnoreCase(event.getClient().getNick()) &&
                getCarbon().getIRCManager().getAdministrators().contains(event.getActor().getName())) {
            event.getChannel().join();
        }
    }
}
