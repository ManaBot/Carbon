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

package uk.jamierocks.mana.carbon.util.intake;

import com.google.common.collect.Lists;
import com.sk89q.intake.Description;
import com.sk89q.intake.Parameter;

import java.util.List;

import javax.annotation.Nullable;

/**
 * A builder for {@link Description}.
 *
 * @author Jamie Mansfield
 * @since 1.1.0
 */
public final class DescriptionBuilder {

    private List<Parameter> parameters = Lists.newArrayList();
    private String shortDescription = "";
    private String help = "";
    private String usage = "";
    private List<String> permissions = Lists.newArrayList();

    /**
     * Creates a new description builder.
     *
     * @since 1.1.0
     */
    public DescriptionBuilder() {
    }

    /**
     * Sets the parameters value for the {@link Description}.
     *
     * @param parameters The list of parameters
     * @see Description#getParameters()
     * @since 1.1.0
     */
    public DescriptionBuilder parameters(List<Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * Sets the short description value for the {@link Description}.
     *
     * @param shortDescription The short description
     * @see Description#getShortDescription()
     * @since 1.1.0
     */
    public DescriptionBuilder shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    /**
     * Sets the help value for the {@link Description}.
     *
     * @param help The help
     * @see Description#getHelp()
     * @since 1.1.0
     */
    public DescriptionBuilder help(String help) {
        this.help = help;
        return this;
    }

    /**
     * Sets the usage value for the {@link Description}.
     *
     * @param usage The usage
     * @see Description#getUsage()
     * @since 1.1.0
     */
    public DescriptionBuilder usage(String usage) {
        this.usage = usage;
        return this;
    }

    /**
     * Sets the permissions value for the {@link Description}.
     *
     * @param permissions The list of permissions
     * @see Description#getPermissions()
     * @since 1.1.0
     */
    public DescriptionBuilder permissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    /**
     * Builds a {@link Description} from the given values.
     *
     * @return The description
     * @since 1.1.0
     */
    public Description build() {
        return new Description() {
            @Override
            public List<Parameter> getParameters() {
                return parameters;
            }

            @Nullable
            @Override
            public String getShortDescription() {
                return shortDescription;
            }

            @Nullable
            @Override
            public String getHelp() {
                return help;
            }

            @Override
            public String getUsage() {
                return usage;
            }

            @Override
            public List<String> getPermissions() {
                return permissions;
            }
        };
    }
}
