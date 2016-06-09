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

import com.google.common.eventbus.EventBus;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Holds all the necessary components for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public final class Carbon {

    /**
     * This will be forcefully overridden by Carbon upon its initialisation.
     */
    private static final Carbon INSTANCE = null;

    /**
     * Gets the instance of {@link Carbon} currently running.
     *
     * @return The current instance of Carbon
     * @since 1.0.0
     */
    public static Carbon getCarbon() {
        checkNotNull(INSTANCE, "INSTANCE is null!");
        return INSTANCE;
    }

    private final EventBus eventBus;

    protected Carbon() {
        this.setInstance(); // Forcefully sets the instance

        this.eventBus = new EventBus();
    }

    private void setInstance() {
        try {
            Field instanceField = Carbon.class.getDeclaredField("INSTANCE");
            instanceField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(instanceField, instanceField.getModifiers() & ~Modifier.FINAL);

            instanceField.set(null, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // If this ever occurs something massively wrong is going on.
            // It is probably for the best to exit the application
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Gets the event bus used by Carbon.
     *
     * @return Carbon's event bus
     * @since 1.0.0
     */
    public EventBus getEventBus() {
        return this.eventBus;
    }
}
