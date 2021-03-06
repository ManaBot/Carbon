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

package uk.jamierocks.mana.carbon.util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * All of Carbon's constant values.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class Constants {

    /**
     * The version of Carbon running.
     *
     * @since 1.0.0
     */
    public static final String VERSION = "%version%";

    /**
     * The path of the Carbon configuration file.
     *
     * @since 2.0.0
     */
    public static final Path CONFIG_PATH = Paths.get("carbon.conf");

    /**
     * The path of the Carbon ops file.
     *
     * @since 2.0.0
     */
    public static final Path OPS_PATH = Paths.get("ops.json");
}
