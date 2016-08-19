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

package uk.jamierocks.mana.carbon.irc;

import static com.google.common.base.Preconditions.checkNotNull;

import org.kitteh.irc.client.library.Client;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * A manager for IRC networks / servers.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface IRCManager {

    /**
     * Returns the {@link Client} for the given id, if available.
     *
     * @param id The client id
     * @return The client
     * @since 1.0.0
     */
    Optional<Client> getClient(String id);

    /**
     * Returns an immutable collection of all the IRC clients.
     *
     * @return The clients
     * @since 1.0.0
     */
    Collection<Client> getClients();

    /**
     * Returns an immutable list of all the bot administrators.
     *
     * @return The administrators
     * @since 1.1.0
     */
    List<String> getAdministrators();

    /**
     * Registers the given listener to all of the IRC clients.
     *
     * @param listener The listener
     * @since 1.2.0
     */
    default void registerIRCEventListener(Object listener) {
        checkNotNull(listener);
        this.getClients().forEach(c -> c.getEventManager().registerEventListener(listener));
    }
}
