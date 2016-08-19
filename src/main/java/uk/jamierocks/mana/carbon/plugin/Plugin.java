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

package uk.jamierocks.mana.carbon.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used by plugins, to signify that they are a plugin.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {

    /**
     * Gets the identifier of the plugin.
     *
     * @return The identifier
     * @since 1.0.0
     */
    String id();

    /**
     * Gets the name of the plugin.
     *
     * @return The name
     * @since 1.0.0
     */
    String name();

    /**
     * Gets the version of the plugin.
     *
     * @return The version
     * @since 1.0.0
     */
    String version();
}
