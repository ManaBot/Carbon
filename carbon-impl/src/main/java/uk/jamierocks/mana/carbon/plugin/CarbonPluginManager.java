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

package uk.jamierocks.mana.carbon.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import uk.jamierocks.mana.carbon.Carbon;
import uk.jamierocks.mana.carbon.CarbonImpl;
import uk.jamierocks.mana.carbon.config.CarbonConfigManager;
import uk.jamierocks.mana.carbon.guice.PluginGuiceModule;
import uk.jamierocks.mana.carbon.service.exception.ExceptionReporter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.jar.JarFile;

/**
 * The implementation of {@link PluginManager} for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class CarbonPluginManager implements PluginManager {

    private static final File PLUGINS_DIR = new File("plugins");

    static {
        if (!PLUGINS_DIR.exists()) {
            PLUGINS_DIR.mkdirs();
        }
    }

    private final Map<String, PluginContainer> plugins = Maps.newHashMap();
    private final Map<Object, PluginContainer> instanceToContainer = Maps.newHashMap();
    private final Map<PluginContainer, Object> containerToInstance = Maps.newHashMap();

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PluginContainer> fromInstance(Object instance) {
        checkNotNull(instance, "instance is null!");

        if (instance instanceof PluginContainer) {
            return Optional.of((PluginContainer) instance);
        }

        return Optional.ofNullable(this.instanceToContainer.get(instance));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PluginContainer> getPlugin(String id) {
        checkNotNull(id, "id is null!");
        return Optional.ofNullable(this.plugins.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PluginContainer> getPlugins() {
        return Collections.unmodifiableCollection(this.plugins.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded(String id) {
        checkNotNull(id, "id is null!");
        return this.plugins.containsKey(id);
    }

    /**
     * Finds and loads all the plugins.
     *
     * @since 1.0.0
     */
    public void loadAllPlugins() {
        File[] jarFiles = PLUGINS_DIR.listFiles(file -> {
            return file.getName().endsWith(".jar");
        });

        for (File jarFile : jarFiles) {
            final List<Class> mods = this.findPlugins(jarFile);

            for (Class<?> pluginClass : mods) {
                final Plugin pluginAnnotation = pluginClass.getDeclaredAnnotation(Plugin.class);
                final CommentedConfigurationNode node = CarbonConfigManager.getPluginConfig(pluginAnnotation);

                if (node != null) {
                    final PluginContainer container = new CarbonPluginContainer(this, pluginAnnotation, node);
                    final Injector injector = Guice.createInjector(new PluginGuiceModule(container));

                    this.loadPlugin(container, injector.getInstance(pluginClass));
                }
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
                    new URL[]{jar.toURI().toURL()}, CarbonPluginManager.class.getClassLoader());

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
                            ExceptionReporter.report("Exception while loading plugin!", e);
                        }
                    }
                });
            } catch (IOException e) {
                ExceptionReporter.report("Exception while loading plugin!", e);
            }
        } catch (MalformedURLException e) {
            ExceptionReporter.report("Exception while loading plugin!", e);
        }

        return pluginClasses;
    }

    /**
     * Registers the given {@link PluginContainer}.
     *
     * @param container The plugin container
     * @param object The plugin instance
     * @since 2.0.0
     */
    public void loadPlugin(PluginContainer container, Object object) {
        CarbonImpl.LOGGER.info("Found plugin: " + container.getName() + " (" + container.getId() + ")");
        Carbon.getCarbon().getEventBus().register(object);
        this.plugins.put(container.getId(), container);
        this.instanceToContainer.put(object, container);
        this.containerToInstance.put(container, object);
    }

    public Optional<Object> getInstance(PluginContainer container) {
        return Optional.ofNullable(this.containerToInstance.get(container));
    }
}
