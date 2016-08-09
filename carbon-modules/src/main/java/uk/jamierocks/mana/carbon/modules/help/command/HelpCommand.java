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
