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

package uk.jamierocks.mana.carbon.bootstrap.dependency;

/**
 * Represents a dependency.
 *
 * @author Jamie Mansfield
 * @since 2.0.0
 */
public final class Dependency {

    private final String basePath;

    public Dependency(String basePath) {
        this.basePath = basePath;
    }

    public static Dependency of(String dep) {
        final String[] split = dep.split(":");
        final String group = split[0];
        final String artifact = split[1];
        final String version = split[2];

        final StringBuilder builder = new StringBuilder();
        builder.append(group.replace(".", "/")).append("/");
        builder.append(artifact).append("/");
        builder.append(version).append("/");
        builder.append(artifact).append("-").append(version);

        return new Dependency(builder.toString());
    }

    /**
     * Gets the base path of the dependency.
     *
     * @return The base path
     * @since 2.0.0
     */
    public String getBasePath() {
        return this.basePath;
    }

    /**
     * Gets the jar path of the dependency.
     *
     * @return The jar path
     * @since 2.0.0
     */
    public String getJarPath() {
        return this.basePath + ".jar";
    }

    /**
     * Gets the jar md5 path of the dependency.
     *
     * @return The jar md5 path
     * @since 2.0.0
     */
    public String getJarMd5Path() {
        return this.basePath + ".jar.md5";
    }
}
