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

package uk.jamierocks.mana.carbon.event.command;

import com.sk89q.intake.CommandMapping;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import uk.jamierocks.mana.carbon.Carbon;
import uk.jamierocks.mana.carbon.event.CancellableEvent;

import java.util.Optional;

/**
 * A event for when commands are executed.
 *
 * @author Jamie Mansfield
 * @since 1.1.0
 */
public class CommandEvent implements CancellableEvent {

    private final ChannelMessageEvent messageEvent;
    private final CommandMapping commandMapping;
    private boolean cancelled = false;

    /**
     * Creates a command event, with the given message event and command mapping.
     *
     * @param messageEvent The cause
     * @param commandMapping The command
     * @since 1.1.0
     */
    public CommandEvent(ChannelMessageEvent messageEvent, CommandMapping commandMapping) {
        this.messageEvent = messageEvent;
        this.commandMapping = commandMapping;
    }

    /**
     * Returns the command to be executed.
     *
     * @return The command
     * @since 1.1.0
     */
    public CommandMapping getCommand() {
        return this.commandMapping;
    }

    /**
     * Returns the {@link Channel} that the command was entered to.
     *
     * @return The channel
     * @since 1.1.0
     */
    public Channel getChannel() {
        return this.messageEvent.getChannel();
    }

    /**
     * Returns the {@link User} that the command was entered by.
     *
     * @return The user
     * @since 1.1.0
     */
    public User getUser() {
        return this.messageEvent.getActor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Object> getCause() {
        return Optional.of(this.messageEvent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandEvent post() {
        Carbon.getCarbon().getEventBus().post(this);
        return this;
    }
}
