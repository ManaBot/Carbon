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

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import uk.jamierocks.mana.carbon.Main;
import uk.jamierocks.mana.carbon.bootstrap.dependency.Dependency;
import uk.jamierocks.mana.carbon.bootstrap.dependency.DependencyManager;
import uk.jamierocks.mana.carbon.bootstrap.util.BootstrapConstants;

/**
 * The bootstrap entry-point.
 *
 * @author Jamie Mansifield
 * @since 2.0.0
 */
public final class Bootstrap {

    public static void main(String[] args) throws Exception {
        // Get dependencies config
        final ConfigurationNode node = GsonConfigurationLoader.builder().setURL(Bootstrap.class.getResource("/dependencies.json")).build().load();
        final BootstrapConfiguration configuration = new BootstrapConfiguration(node);

        // Check all the dependencies
        final DependencyManager dependencyManager = new DependencyManager(BootstrapConstants.LIBRARIES_PATH);

        for (BootstrapConfiguration.Dependency dependency : configuration.getDependencies()) {
            final Dependency dep = Dependency.of(dependency.getName());
            dependencyManager.checkDependency(dependency.getRepo(), dep);
        }

        // Run Carbon
        Main.main(args);
    }
}
