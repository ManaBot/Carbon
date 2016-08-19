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

package uk.jamierocks.mana.carbon.util.command;

import static uk.jamierocks.mana.carbon.Carbon.getCarbon;

import com.sk89q.intake.CommandException;
import com.sk89q.intake.InvalidUsageException;
import com.sk89q.intake.InvocationCommandException;
import com.sk89q.intake.argument.Namespace;
import com.sk89q.intake.util.auth.AuthorizationException;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.lib.net.engio.mbassy.listener.Handler;
import uk.jamierocks.mana.carbon.event.command.CommandEvent;
import uk.jamierocks.mana.carbon.service.exception.ExceptionReporter;
import uk.jamierocks.mana.carbon.util.Constants;

import java.util.Collections;

/**
 * A listener for commands.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class CommandListener {

    @Handler
    public void onMessageRecieved(ChannelMessageEvent event) {
        final String message = event.getMessage();
        if (message.startsWith(Constants.COMMAND_PREFIX)) {
            // By this point it still isn't decided as to weather this is a command!
            final String command = message.substring(Constants.COMMAND_PREFIX.length());

            if (getCarbon().getCommandDispatcher().contains(command.split(" ")[0])) {
                // We now know it is a command, and can continue to process it
                getCarbon().getLogger().info(event.getActor().getNick() + " issued command: " + command);

                CommandEvent commandEvent =
                        new CommandEvent(event, getCarbon().getCommandDispatcher().get(command.split(" ")[0])).post();
                if (!commandEvent.isCancelled()) {
                    Namespace namespace = new Namespace();
                    namespace.put(String.class, command);
                    namespace.put(Channel.class, event.getChannel());
                    namespace.put(User.class, event.getActor());

                    try {
                        getCarbon().getCommandDispatcher()
                                .call(command, namespace, Collections.singletonList(command));
                    } catch (InvalidUsageException e) {
                        event.getActor().sendMessage("Usage: " + e.getCommand().getDescription().getUsage());
                    } catch (CommandException | InvocationCommandException e) {
                        ExceptionReporter.report("Failed to execute command: " + message, e);
                    } catch (AuthorizationException e) {
                        event.sendReply(event.getActor().getNick() + ": You do not have permission to do that!");
                    }
                }
            }
        }
    }
}
