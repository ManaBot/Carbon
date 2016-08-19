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

        public Dependency(ConfigurationNode node) {
            this.repo = node.getNode("repo").getString();
            this.name = node.getNode("name").getString();
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
    }
}
