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
 * Represents an event that can be cancelled.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface CancellableEvent extends Event {

    /**
     * Gets if the event has been cancelled.
     *
     * @return {@code True} if the event has been cancelled, {@code false} otherwise
     * @since 1.0.0
     */
    boolean isCancelled();

    /**
     * Sets if the event has been cancelled.
     *
     * @param cancel If the event is cancelled or not
     * @since 1.0.0
     */
    void setCancelled(boolean cancel);
}