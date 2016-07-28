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
