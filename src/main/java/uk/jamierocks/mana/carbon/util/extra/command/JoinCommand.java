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

package uk.jamierocks.mana.carbon.util.extra.command;

import static uk.jamierocks.mana.carbon.Carbon.getCarbon;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.sk89q.intake.CommandCallable;
import com.sk89q.intake.CommandException;
import com.sk89q.intake.Description;
import com.sk89q.intake.InvocationCommandException;
import com.sk89q.intake.argument.Namespace;
import com.sk89q.intake.util.auth.AuthorizationException;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.kitteh.irc.client.library.element.User;
import org.slf4j.Logger;

import java.util.List;

/**
 * A join command for the invite module.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class JoinCommand implements CommandCallable {

    private final Logger logger;

    public JoinCommand(Logger logger) {
        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean call(String arguments, Namespace namespace, List<String> parentCommands)
            throws CommandException, InvocationCommandException, AuthorizationException {
        final String[] channelSplit = arguments.split("/");

        if (getCarbon().getIRCManager().getClient(channelSplit[0]).isPresent()) {
            getCarbon().getIRCManager().getClient(channelSplit[0]).get().addChannel(channelSplit[1]);
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Description getDescription() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean testPermission(Namespace namespace) {
        try {
            return getCarbon().getConfigurationNode().getNode("irc", "admins").getList(TypeToken.of(String.class))
                    .contains(namespace.get(User.class).getName());
        } catch (ObjectMappingException e) {
            this.logger.error("Failed to get IRC admins!", e);
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getSuggestions(String arguments, Namespace locals) throws CommandException {
        return Lists.newArrayList();
    }
}
