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
            final Dependency dep;
            if (dependency.getName() != null) {
                dep = Dependency.of(dependency.getName());
            } else {
                dep = new Dependency(dependency.getLocation());
            }

            dependencyManager.checkDependency(dependency.getRepo(), dep);
        }

        // Run Carbon
        Main.main(args);
    }
}
