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

        public Irc(CommentedConfigurationNode node) {
            for (CommentedConfigurationNode network : node.getNode("networks").getChildrenList()) {
                this.networks.add(new Network(network));
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
