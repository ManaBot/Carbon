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

package uk.jamierocks.mana.carbon.util.command;

import com.sk89q.intake.CommandException;
import com.sk89q.intake.InvocationCommandException;
import com.sk89q.intake.argument.Namespace;
import com.sk89q.intake.util.auth.AuthorizationException;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.lib.net.engio.mbassy.listener.Handler;
import uk.jamierocks.mana.carbon.Carbon;
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

            if (Carbon.getCarbon().getCommandDispatcher().contains(command.split(" ")[0])) {
                // We know know it is a command, and can continue to process it
                Namespace namespace = new Namespace();
                namespace.put(String.class, command);
                namespace.put(Channel.class, event.getChannel());
                namespace.put(User.class, event.getActor());

                try {
                    Carbon.getCarbon().getCommandDispatcher()
                            .call(command, namespace, Collections.singletonList(command));
                } catch (CommandException | InvocationCommandException e) {
                    Carbon.getCarbon().getLogger().error("Failed to execute command: " + message, e);
                } catch (AuthorizationException e) {
                    event.sendReply(event.getActor().getNick() + ": You do not have permission to do that!");
                }
            }
        }
    }
}
