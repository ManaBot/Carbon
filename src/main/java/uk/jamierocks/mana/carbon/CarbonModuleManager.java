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

package uk.jamierocks.mana.carbon;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import uk.jamierocks.mana.carbon.module.Module;
import uk.jamierocks.mana.carbon.module.ModuleContainer;
import uk.jamierocks.mana.carbon.module.ModuleManager;
import uk.jamierocks.mana.carbon.util.guice.ModuleGuiceModule;

import java.util.List;

/**
 * The implementation of {@link ModuleManager} for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public class CarbonModuleManager implements ModuleManager {

    private final List<ModuleContainer> modules = Lists.newArrayList();

    protected CarbonModuleManager() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerModule(Class<?> module) {
        checkNotNull(module, "module is null!");

        if (module.isAnnotationPresent(Module.class)) {
            Module moduleAnnotation = module.getDeclaredAnnotation(Module.class);

            Carbon.getCarbon().getLogger()
                    .info("Loading module: " + moduleAnnotation.name() + "(" + moduleAnnotation.id() + ")");

            Injector injector = Guice.createInjector(new ModuleGuiceModule(moduleAnnotation));
            Object instance = injector.getInstance(module);

            Carbon.getCarbon().getEventBus().register(instance);
            this.modules.add(ModuleContainer.of(moduleAnnotation, instance));

            Carbon.getCarbon().getLogger()
                    .info("Loaded module: " + moduleAnnotation.name() + "(" + moduleAnnotation.id() + ")");
        } else {
            Carbon.getCarbon().getLogger().error(module.getName() + " has no @Module annotation!");
        }
    }
}
