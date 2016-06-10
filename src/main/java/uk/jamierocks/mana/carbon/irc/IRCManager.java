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

package uk.jamierocks.mana.carbon.irc;

import org.kitteh.irc.client.library.Client;

import java.util.Collection;
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
}
