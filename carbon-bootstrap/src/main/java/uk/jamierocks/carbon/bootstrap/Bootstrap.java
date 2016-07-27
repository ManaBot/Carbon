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

package uk.jamierocks.carbon.bootstrap;

import uk.jamierocks.carbon.bootstrap.util.BootstrapConstants;
import uk.jamierocks.mana.carbon.Main;

/**
 * The bootstrap entry-point.
 *
 * @author Jamie Mansifield
 * @since 2.0.0
 */
public final class Bootstrap {

    public static void main(String[] args) throws Exception {
        final DependencyManager dependencyManager = new DependencyManager(BootstrapConstants.LIBRARIES_PATH);

        // Basic dependencies
        dependencyManager.checkDependency(BootstrapConstants.MAVEN_CENTRAL, "com/google/guava/guava/19.0/guava-19.0.jar");
        dependencyManager.checkDependency(BootstrapConstants.MAVEN_CENTRAL, "com/google/inject/guice/4.0/guice-4.0.jar");

        // Configurate dependencies
        dependencyManager.checkDependency(BootstrapConstants.MAVEN_CENTRAL,
                "ninja/leaping/configurate/configurate-core/3.1.1/configurate-core-3.1.1.jar");
        dependencyManager.checkDependency(BootstrapConstants.MAVEN_CENTRAL,
                "ninja/leaping/configurate/configurate-hocon/3.1.1/configurate-hocon-3.1.1.jar");

        // Logging dependencies
        dependencyManager.checkDependency(BootstrapConstants.MAVEN_CENTRAL, "org/slf4j/slf4j-api/1.7.21/slf4j-api-1.7.21.jar");
        dependencyManager.checkDependency(BootstrapConstants.MAVEN_CENTRAL, "org/apache/logging/log4j/log4j-api/2.6.1/log4j-api-2.6.1.jar");
        dependencyManager.checkDependency(BootstrapConstants.MAVEN_CENTRAL, "org/apache/logging/log4j/log4j-core/2.6.1/log4j-core-2.6.1.jar");
        dependencyManager.checkDependency(BootstrapConstants.MAVEN_CENTRAL,
                "org/apache/logging/log4j/log4j-slf4j-impl/2.6.1/log4j-slf4j-impl-2.6.1.jar");

        // IRC dependencies
        dependencyManager.checkDependency(BootstrapConstants.MAVEN_CENTRAL, "org/kitteh/irc/client-lib/1.1.0/client-lib-1.1.0.jar");

        // Command dependencies
        dependencyManager.checkDependency(BootstrapConstants.MISERABLE_REPO,
                "com/sk89q/intake/intake/4.2-MISNIN-SNAPSHOT/intake-4.2-MISNIN-20160413.183647-1.jar");

        Main.main(args);
    }
}
