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
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import uk.jamierocks.mana.carbon.plugin.Plugin;
import uk.jamierocks.mana.carbon.plugin.PluginContainer;
import uk.jamierocks.mana.carbon.plugin.PluginManager;
import uk.jamierocks.mana.carbon.util.guice.PluginGuiceModule;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.jar.JarFile;

/**
 * The manager for Carbon plugins.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class CarbonPluginManager implements PluginManager {

    private static final File PLUGIN_DIR = new File("plugins");

    private final Map<String, PluginContainer> plugins = Maps.newHashMap();
    private final Map<Object, PluginContainer> pluginInstances = Maps.newHashMap();

    protected CarbonPluginManager() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PluginContainer> fromInstance(Object instance) {
        checkNotNull(instance, "instance is null!");

        if (instance instanceof PluginContainer) {
            return Optional.of((PluginContainer) instance);
        }

        return Optional.ofNullable(this.pluginInstances.get(instance));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PluginContainer> getPlugin(String id) {
        return Optional.ofNullable(this.plugins.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PluginContainer> getPlugins() {
        return Lists.newArrayList(this.plugins.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded(String id) {
        return this.plugins.containsKey(id);
    }

    /**
     * Finds and loads all the plugins.
     *
     * @since 1.0.0
     */
    protected void loadAllPlugins() {
        // Register the Carbon plugin
        this.registerPlugin(Carbon.CONTAINER);

        File[] jarFiles = PLUGIN_DIR.listFiles(file -> {
            return file.getName().endsWith(".jar");
        });

        for (File jarFile : jarFiles) {
            List<Class> mods = this.findPlugins(jarFile);

            for (Class<?> pluginClass : mods) {
                Plugin pluginAnnotation = pluginClass.getDeclaredAnnotation(Plugin.class);

                Injector injector = Guice.createInjector(new PluginGuiceModule(pluginAnnotation));
                Object instance = injector.getInstance(pluginClass);

                this.registerPlugin(PluginContainer.of(pluginAnnotation, instance));
            }
        }
    }

    /**
     * Finds all the plugins in a given jar file.
     *
     * @param jar The jar file
     * @return All the plugins
     * @since 1.0.0
     */
    private List<Class> findPlugins(File jar) {
        List<Class> pluginClasses = Lists.newArrayList();

        try {
            URLClassLoader classLoader = new URLClassLoader(
                    new URL[]{ jar.toURI().toURL() }, CarbonPluginManager.class.getClassLoader());

            try (JarFile jarFile = new JarFile(jar)) {
                jarFile.stream().forEach(jarEntry -> {
                    if (!jarEntry.isDirectory() && jarEntry.getName().endsWith(".class")) {
                        String className = jarEntry.getName().replace('/', '.');

                        try {
                            Class<?> pluginClass = classLoader.loadClass(
                                    className.substring(0, className.length() - ".class".length()));
                            if (pluginClass.isAnnotationPresent(Plugin.class)) {
                                pluginClasses.add(pluginClass);
                            }
                        } catch (ClassNotFoundException e) {
                            Carbon.getCarbon().getLogger().error("Exception while loading plugin!", e);
                        }
                    }
                });
            } catch (IOException e) {
                Carbon.getCarbon().getLogger().error("Exception while loading plugin!", e);
            }
        } catch (MalformedURLException e) {
            Carbon.getCarbon().getLogger().error("Exception while loading plugin!", e);
        }

        return pluginClasses;
    }

    /**
     * Registers the given {@link PluginContainer}.
     *
     * @param container The plugin container
     * @since 1.0.0
     */
    private void registerPlugin(PluginContainer container) {
        Carbon.getCarbon().getEventBus().register(container.getInstance());
        this.plugins.put(container.getId(), container);
        this.pluginInstances.put(container.getInstance(), container);
    }
}
