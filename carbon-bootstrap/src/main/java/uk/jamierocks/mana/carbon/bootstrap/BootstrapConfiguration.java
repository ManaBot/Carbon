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

package uk.jamierocks.mana.carbon.bootstrap;

import com.google.common.collect.Lists;
import ninja.leaping.configurate.ConfigurationNode;

import java.util.List;

/**
 * Represents the Carbon Bootstrap configuration.
 *
 * @author Jamie Mansfield
 * @since 2.0.0
 */
public final class BootstrapConfiguration {

    private List<Dependency> dependencies = Lists.newArrayList();

    public BootstrapConfiguration(ConfigurationNode node) {
        for (ConfigurationNode dep : node.getNode("dependencies").getChildrenList()) {
            this.dependencies.add(new Dependency(dep));
        }
    }

    /**
     * Returns an immutable list of all the dependencies.
     *
     * @return The dependencies
     * @since 2.0.0
     */
    public List<Dependency> getDependencies() {
        return Lists.newArrayList(this.dependencies);
    }

    /**
     * Represents a dependency
     *
     * @since 2.0.0
     */
    public static class Dependency {

        private String repo;
        private String name;
        private String location;

        public Dependency(ConfigurationNode node) {
            this.repo = node.getNode("repo").getString();
            this.name = node.getNode("name").getString();
            this.location = node.getNode("location").getString();
        }

        /**
         * Gets the repo url for the dependency.
         *
         * @return The repo url
         * @since 2.0.0
         */
        public String getRepo() {
            return this.repo;
        }

        /**
         * Gets the name of the dependency.
         *
         * @return The name
         * @since 2.0.0
         */
        public String getName() {
            return this.name;
        }

        /**
         * Gets the location of the dependency.
         *
         * @return The location
         * @since 2.0.0
         */
        public String getLocation() {
            return this.location;
        }
    }
}
