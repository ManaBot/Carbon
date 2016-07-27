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

import com.google.common.collect.Lists;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

import java.util.List;

/**
 * Represents the Carbon configuration.
 *
 * @author Jamie Mansfield
 * @since 2.0.0
 */
public final class CarbonConfiguration {

    private final CommentedConfigurationNode node;
    private Irc irc;
    private Commands commands;

    public CarbonConfiguration(CommentedConfigurationNode node) {
        this.node = node;
        this.irc = new Irc(node);
        this.commands = new Commands(node.getNode("commands"));
    }

    /**
     * Gets the configuration node.
     *
     * @return The node
     * @since 2.0.0
     */
    public CommentedConfigurationNode getNode() {
        return this.node;
    }

    /**
     * Gets the irc configuration.
     *
     * @return the irc config
     * @since 2.0.0
     */
    public Irc getIrc() {
        return this.irc;
    }

    /**
     * Gets the command configuration.
     *
     * @return the command config
     * @since 2.0.0
     */
    public Commands getCommands() {
        return this.commands;
    }

    /**
     * Represents the irc configuration.
     *
     * @since 2.0.0
     */
    public static class Irc {

        private List<Network> networks = Lists.newArrayList();
        private List<String> admins = Lists.newArrayList();

        public Irc(CommentedConfigurationNode node) {
            for (CommentedConfigurationNode network : node.getNode("networks").getChildrenList()) {
                this.networks.add(new Network(network));
            }
            for (CommentedConfigurationNode admin : node.getNode("admins").getChildrenList()) {
                this.admins.add(admin.getString());
            }
        }

        /**
         * Returns an immutable list of all the bot networks.
         *
         * @return The networks
         * @since 2.0.0
         */
        public List<Network> getNetworks() {
            return Lists.newArrayList(this.networks);
        }

        /**
         * Returns an immutable list of all the bot administrators.
         *
         * @return The admins
         * @since 2.0.0
         */
        public List<String> getAdmins() {
            return Lists.newArrayList(this.admins);
        }

        /**
         * Represents an IRC network.
         *
         * @since 2.0.0
         */
        public static class Network {

            private String id;
            private String host;
            private int port;
            private boolean secure;
            private String username;
            private String nickname;

            public Network(CommentedConfigurationNode node) {
                this.id = node.getNode("id").getString();
                this.host = node.getNode("host").getString();
                this.port = node.getNode("port").getInt();
                this.secure = node.getNode("secure").getBoolean();
                this.username = node.getNode("username").getString();
                this.nickname = node.getNode("nickname").getString();
            }

            /**
             * Gets the id of the network.
             *
             * @return The id
             * @since 2.0.0
             */
            public String getId() {
                return this.id;
            }

            /**
             * Gets the host of the network.
             *
             * @return The host
             * @since 2.0.0
             */
            public String getHost() {
                return this.host;
            }

            /**
             * Gets the port of the network.
             *
             * @return The port
             * @since 2.0.0
             */
            public int getPort() {
                return this.port;
            }

            /**
             * Gets if the network uses ssl of the network.
             *
             * @return {@code true} if the network uses ssl, {@code false} otherwise
             * @since 2.0.0
             */
            public boolean isSecure() {
                return this.secure;
            }

            /**
             * Gets the username of the network.
             *
             * @return The username
             * @since 2.0.0
             */
            public String getUsername() {
                return this.username;
            }

            /**
             * Gets the nickname of the network.
             *
             * @return The nickname
             * @since 2.0.0
             */
            public String getNickname() {
                return this.nickname;
            }
        }
    }

    /**
     * Represents the command configuration.
     *
     * @since 2.0.0
     */
    public static class Commands {

        private String prefix;

        public Commands(CommentedConfigurationNode node) {
            this.prefix = node.getNode("prefix").getString(".");
        }

        /**
         * Gets the global command prefix.
         *
         * @return The command prefix.
         * @since 2.0.0
         */
        public String getPrefix() {
            return this.prefix;
        }
    }
}
