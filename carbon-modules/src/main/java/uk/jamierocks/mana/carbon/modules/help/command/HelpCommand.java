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

package uk.jamierocks.mana.carbon.modules.help.command;

import com.google.common.collect.Lists;
import com.sk89q.intake.CommandCallable;
import com.sk89q.intake.CommandException;
import com.sk89q.intake.CommandMapping;
import com.sk89q.intake.Description;
import com.sk89q.intake.context.CommandLocals;
import com.sk89q.intake.util.auth.AuthorizationException;
import org.kitteh.irc.client.library.element.User;
import uk.jamierocks.mana.carbon.Carbon;
import uk.jamierocks.mana.carbon.util.intake.DescriptionBuilder;

import java.util.List;

/**
 * A help command for the help module.
 *
 * @author Jamie Mansfield
 * @since 1.1.0
 */
public final class HelpCommand implements CommandCallable {

    private static final Description DESCRIPTION = new DescriptionBuilder()
            .help("Displays all commands, with their help text")
            .usage("help [command]")
            .build();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean call(String arguments, CommandLocals namespace, String[] parentCommands) throws CommandException, AuthorizationException {
        if (arguments != null && !arguments.equals("")) {
            if (Carbon.getCarbon().getCommandDispatcher().contains(arguments)) {
                CommandMapping mapping = Carbon.getCarbon().getCommandDispatcher().get(arguments);
                if (mapping.getCallable().testPermission(namespace)) {
                    StringBuilder builder = new StringBuilder();

                    builder.append("Command: ");
                    builder.append(mapping.getPrimaryAlias());
                    builder.append(" (");
                    builder.append(mapping.getCallable().getDescription().getHelp());
                    builder.append(") ");
                    builder.append("Usage: ");
                    builder.append(mapping.getDescription().getUsage());

                    namespace.get(User.class).sendMessage(builder.toString());
                } else {
                    namespace.get(User.class)
                            .sendMessage("You do not have permission to view the help for that command!");
                }
            } else {
                namespace.get(User.class).sendMessage("Command not found!");
            }
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("Commands: ");

            for (CommandMapping mapping : Carbon.getCarbon().getCommandDispatcher().getCommands()) {
                if (mapping.getCallable().testPermission(namespace)) {
                    builder.append(mapping.getPrimaryAlias());
                    builder.append(" (");
                    builder.append(mapping.getCallable().getDescription().getHelp());
                    builder.append(") ");
                }
            }

            namespace.get(User.class).sendMessage(builder.toString());
        }

        return true;
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
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getSuggestions(String arguments, CommandLocals locals) throws CommandException {
        return Lists.newArrayList();
    }
}
