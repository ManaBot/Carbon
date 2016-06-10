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

import com.google.common.collect.Maps;
import org.kitteh.irc.client.library.Client;
import uk.jamierocks.mana.carbon.irc.IRCManager;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * The implementation of {@link IRCManager} for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class CarbonIRCManager implements IRCManager {

    private final Map<String, Client> clients = Maps.newHashMap();

    protected CarbonIRCManager() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Client> getClient(String id) {
        return Optional.ofNullable(this.clients.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Client> getClients() {
        return Collections.unmodifiableCollection(this.clients.values());
    }
}
