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

package uk.jamierocks.mana.carbon.modules.invite;

import static uk.jamierocks.mana.carbon.Carbon.getCarbon;

import com.google.common.eventbus.Subscribe;
import org.kitteh.irc.client.library.event.channel.ChannelInviteEvent;
import org.kitteh.irc.lib.net.engio.mbassy.listener.Handler;
import uk.jamierocks.mana.carbon.event.state.PostInitialisationEvent;
import uk.jamierocks.mana.carbon.module.Module;
import uk.jamierocks.mana.carbon.modules.invite.command.JoinCommand;
import uk.jamierocks.mana.carbon.modules.invite.command.PartCommand;

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
        event.getCarbon().getIRCManager().registerIRCEventListener(this);

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
