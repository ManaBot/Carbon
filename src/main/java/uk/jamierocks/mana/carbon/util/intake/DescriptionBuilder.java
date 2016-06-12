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
    public DescriptionBuilder() {}

    public DescriptionBuilder parameters(List<Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    public DescriptionBuilder shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public DescriptionBuilder help(String help) {
        this.help = help;
        return this;
    }

    public DescriptionBuilder usage(String usage) {
        this.usage = usage;
        return this;
    }

    public DescriptionBuilder permissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

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
