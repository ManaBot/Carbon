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
            return HoconConfigurationLoader.builder().setPath(configPath).build().load();
        } catch (IOException e) {
            ExceptionReporter.report("Failed to load plugin conf. Skipping plugin!", e);
            return null;
        }
    }
}
