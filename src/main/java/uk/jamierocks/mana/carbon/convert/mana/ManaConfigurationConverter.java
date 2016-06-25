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

package uk.jamierocks.mana.carbon.convert.mana;

import com.google.common.collect.Maps;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.commented.SimpleCommentedConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import uk.jamierocks.mana.carbon.convert.ConfigurationConverter;
import uk.jamierocks.mana.carbon.convert.RootConfigurationConverter;
import uk.jamierocks.mana.carbon.service.exception.ExceptionReporter;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * The Mana configuration converter.
 *
 * @author Jamie Mansfield
 * @since 1.2.0
 */
public class ManaConfigurationConverter implements RootConfigurationConverter {

    private Map<String, ConfigurationConverter> childConverters = Maps.newHashMap();

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ConfigurationNode> read(File configFile) {
        try {
            return Optional.of(GsonConfigurationLoader.builder().setFile(configFile).build().load());
        } catch (IOException e) {
            ExceptionReporter.report("Failed to load config file: " + configFile.getName(), e);
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(String key, ConfigurationConverter converter) {
        this.childConverters.put(key, converter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CommentedConfigurationNode> convert(ConfigurationNode configurationNode) {
        CommentedConfigurationNode node = SimpleCommentedConfigurationNode.root();

        for (String key : this.childConverters.keySet()) {
            Optional<CommentedConfigurationNode> childNode =
                    this.childConverters.get(key).convert(configurationNode.getNode(key));
            if (childNode.isPresent()) {
                node.getNode(key).mergeValuesFrom(childNode.get());
            }
        }

        return Optional.empty();
    }
}
