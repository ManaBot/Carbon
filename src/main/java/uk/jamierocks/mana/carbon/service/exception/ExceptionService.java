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

package uk.jamierocks.mana.carbon.service.exception;

/**
 * Represents an exception service for Carbon.
 *
 * @author Jamie Mansfield
 * @since 1.1.0
 */
public interface ExceptionService {

    /**
     * Handles reporting an {@link Exception}, with the default message.
     *
     * @param throwable The exception
     * @since 1.1.0
     */
    default void report(Throwable throwable) {
        this.report("Carbon has experienced an error!", throwable);
    }

    /**
     * Handles reporting an {@link Exception}, with a message.
     *
     * @param message The message
     * @param throwable The exception
     * @since 1.1.0
     */
    void report(String message, Throwable throwable);
}
