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

package uk.jamierocks.mana.carbon;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import uk.jamierocks.mana.carbon.plugin.Plugin;
import uk.jamierocks.mana.carbon.plugin.PluginContainer;
import uk.jamierocks.mana.carbon.plugin.PluginManager;
import uk.jamierocks.mana.carbon.service.exception.ExceptionReporter;
import uk.jamierocks.mana.carbon.util.guice.PluginGuiceModule;

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
    private final Map<Object, PluginContainer> pluginInstances = Maps.newHashMap();

    protected CarbonPluginManager() {
    }

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
    protected void loadAllPlugins() {
        File[] jarFiles = PLUGINS_DIR.listFiles(file -> {
            return file.getName().endsWith(".jar");
        });

        for (File jarFile : jarFiles) {
            final List<Class> mods = this.findPlugins(jarFile);

            for (Class<?> pluginClass : mods) {
                final Plugin pluginAnnotation = pluginClass.getDeclaredAnnotation(Plugin.class);
                final Injector injector = Guice.createInjector(new PluginGuiceModule(pluginAnnotation));

                this.loadPlugin(PluginContainer.of(pluginAnnotation, injector.getInstance(pluginClass)));
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
     * @since 1.3.0
     */
    protected void loadPlugin(PluginContainer container) {
        Carbon.getCarbon().getLogger().info("Found plugin: " + container.getName() + " (" + container.getId() + ")");
        Carbon.getCarbon().getEventBus().register(container.getInstance());
        this.plugins.put(container.getId(), container);
        this.pluginInstances.put(container.getInstance(), container);
    }
}
