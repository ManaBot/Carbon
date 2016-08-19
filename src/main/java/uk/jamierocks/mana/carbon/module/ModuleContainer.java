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

package uk.jamierocks.mana.carbon.module;

import static com.google.common.base.Preconditions.checkNotNull;

import uk.jamierocks.mana.carbon.plugin.PluginContainer;

import java.util.Optional;

/**
 * A wrapper around a module.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface ModuleContainer {

    /**
     * Creates a {@link ModuleContainer} around a {@link Module} and an {@link Object}.
     *
     * @param module The module annotation
     * @param instance The module instance
     * @return The module container
     * @since 1.0.0
     * @deprecated As of release 1.1.0, replaced by {@link #of(Module, Object, PluginContainer)}
     */
    @Deprecated
    static ModuleContainer of(Module module, Object instance) {
        checkNotNull(module, "module is null!");
        checkNotNull(instance, "instance is null!");

        return new ModuleContainer() {
            @Override
            public String getId() {
                return module.id();
            }

            @Override
            public String getName() {
                return module.name();
            }

            @Override
            public Optional<PluginContainer> getOwner() {
                return Optional.empty();
            }

            @Override
            public Object getInstance() {
                return instance;
            }
        };
    }

    /**
     * Creates a {@link ModuleContainer} around a {@link Module}, a {@link Object} and a {@link PluginContainer}.
     *
     * @param module The module annotation
     * @param instance The module instance
     * @param owner The owner of the module
     * @return The module container
     * @since 1.1.0
     */
    static ModuleContainer of(Module module, Object instance, PluginContainer owner) {
        checkNotNull(module, "module is null!");
        checkNotNull(instance, "instance is null!");
        checkNotNull(owner, "owner is null!");

        return new ModuleContainer() {
            @Override
            public String getId() {
                return module.id();
            }

            @Override
            public String getName() {
                return module.name();
            }

            @Override
            public Optional<PluginContainer> getOwner() {
                return Optional.of(owner);
            }

            @Override
            public Object getInstance() {
                return instance;
            }
        };
    }

    /**
     * Gets the identifier of the module.
     *
     * @return The identifier
     * @see Module#id()
     * @since 1.0.0
     */
    String getId();

    /**
     * Gets the name of the module.
     *
     * @return The name
     * @see Module#name()
     * @since 1.0.0
     */
    String getName();

    /**
     * Gets the owner of the module, if available.
     *
     * @return The owner
     * @since 1.1.0
     */
    Optional<PluginContainer> getOwner();

    /**
     * Gets the instance of this module.
     *
     * @return The instance
     * @since 1.0.0
     */
    Object getInstance();
}
