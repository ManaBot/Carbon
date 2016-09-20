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

package uk.jamierocks.mana.carbon.event;

/**
 * The Carbon event bus.
 *
 * @author Jamie Mansfield
 * @since 2.0.0
 */
public interface EventBus {

    /**
     * Registers the given {@link EventListener}.
     *
     * @param plugin The owning plugin
     * @param listener The event listener
     * @since 2.0.0
     */
    void register(Object plugin, EventListener listener);

    /**
     * Registers methods annotated with {@link Listener} in the given Object, or if
     * the Object is a {@link EventListener} then registers that.
     *
     * @param plugin The owning plugin
     * @param listener The listener class, or {@link EventListener}
     * @since 2.0.0
     */
    void register(Object plugin, Object listener);

    /**
     * Posts the given {@link Event} through the event bus.
     *
     * @param event The event
     * @since 2.0.0
     */
    void post(Event event);

}
