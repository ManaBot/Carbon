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

package uk.jamierocks.mana.carbon.event.command;

import static com.google.common.base.Preconditions.checkNotNull;

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
        this.messageEvent = checkNotNull(messageEvent, "messageEvent is null!");
        this.commandMapping = checkNotNull(commandMapping, "commandMappings is null!");
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
