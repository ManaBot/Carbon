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

import uk.jamierocks.mana.carbon.Carbon;

import java.util.Optional;

/**
 * An event within Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public interface Event {

    /**
     * Gets the instance of {@link Carbon}.
     *
     * @return The instance of Carbon
     * @since 1.0.0
     */
    default Carbon getCarbon() {
        return Carbon.getCarbon();
    }

    /**
     * Gets the cause of the event, if available.
     *
     * @return The cause
     * @since 1.1.0
     */
    default Optional<Object> getCause() {
        return Optional.empty();
    }

    /**
     * Posts the {@link Event} to the {@link Carbon} event bus.
     *
     * @return This event
     * @since 1.0.0
     */
    default Event post() {
        Carbon.getCarbon().getEventBus().post(this);
        return this;
    }
}
