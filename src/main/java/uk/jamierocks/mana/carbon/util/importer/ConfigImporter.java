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

package uk.jamierocks.mana.carbon.util.importer;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

import java.io.File;
import java.util.Optional;

/**
 * Represents a configuration importer.
 *
 * @author Jamie Mansfield
 * @since 1.2.0
 */
public abstract class ConfigImporter {

    /**
     * Attempts to convert the given config file.
     *
     * @param configFile The config file
     * @return The Carbon configuration node
     * @since 1.2.0
     */
    public abstract Optional<CommentedConfigurationNode> convert(File configFile);

    /**
     * Registers the given importer, as so that modules can be imported.
     *
     * @param key The key within the original config
     * @param importer The importer
     * @since 1.2.0
     */
    public abstract void register(String key, ConfigImporter importer);
}
