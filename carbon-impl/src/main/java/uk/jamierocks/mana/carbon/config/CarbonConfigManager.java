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

package uk.jamierocks.mana.carbon.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import uk.jamierocks.mana.carbon.plugin.CarbonPluginManager;
import uk.jamierocks.mana.carbon.plugin.Plugin;
import uk.jamierocks.mana.carbon.service.exception.ExceptionReporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The Carbon configuration manager.
 *
 * @author Jamie Mansfield
 * @since 2.0.0
 */
public final class CarbonConfigManager {

    public static CommentedConfigurationNode getPluginConfig(Plugin plugin) {
        final Path configPath = Paths.get("config", plugin.id() + ".conf");

        if (Files.notExists(configPath)) {
            try {
                Files.copy(CarbonPluginManager.class.getResourceAsStream("/plugin.conf"), configPath);
            } catch (IOException e) {
                ExceptionReporter.report("Failed to copy default plugin conf. Skipping plugin!", e);
                return null;
            }
        }

        try {
            final CommentedConfigurationNode node = HoconConfigurationLoader.builder().setPath(configPath).build().load();
            return node;
        } catch (IOException e) {
            ExceptionReporter.report("Failed to load plugin conf. Skipping plugin!", e);
            return null;
        }
    }
}
