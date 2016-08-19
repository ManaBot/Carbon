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

package uk.jamierocks.mana.carbon.modules.invite.command;

import static uk.jamierocks.mana.carbon.Carbon.getCarbon;

import com.google.common.collect.Lists;
import com.sk89q.intake.CommandCallable;
import com.sk89q.intake.CommandException;
import com.sk89q.intake.Description;
import com.sk89q.intake.InvalidUsageException;
import com.sk89q.intake.context.CommandLocals;
import com.sk89q.intake.util.auth.AuthorizationException;
import org.kitteh.irc.client.library.element.User;
import uk.jamierocks.mana.carbon.util.intake.DescriptionBuilder;

import java.util.List;

/**
 * A join command for the invite module.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class JoinCommand implements CommandCallable {

    private static final Description DESCRIPTION = new DescriptionBuilder()
            .help("Joins the given channel, in the format of server/#channel")
            .usage("join <server/#channel>")
            .build();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean call(String arguments, CommandLocals namespace, String[] parentCommand) throws CommandException, AuthorizationException {
        if (arguments != null || !arguments.equals("")) {
            final String[] channelSplit = arguments.split("/");

            if (channelSplit.length == 2 &&
                    channelSplit[0] != null && !channelSplit[0].equals("") &&
                    channelSplit[1] != null && !channelSplit[1].equals("")) {
                if (getCarbon().getIRCManager().getClient(channelSplit[0]).isPresent()) {
                    getCarbon().getIRCManager().getClient(channelSplit[0]).get().addChannel(channelSplit[1]);
                    return true;
                }
            } else {
                throw new InvalidUsageException(this);
            }
        } else {
            throw new InvalidUsageException(this);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Description getDescription() {
        return DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean testPermission(CommandLocals namespace) {
        return getCarbon().getIRCManager().getAdministrators().contains(namespace.get(User.class).getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getSuggestions(String arguments, CommandLocals locals) throws CommandException {
        return Lists.newArrayList();
    }
}
