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

package uk.jamierocks.mana.carbon.service;

/**
 * An exception for issues that may arise while registering a provider.
 *
 * @author Jamie Mansfield
 * @since 1.0.0
 */
public class ProviderRegistrationException extends Exception {

    /**
     * Constructs the exception with only a message.
     *
     * @param message The message
     * @since 1.0.0
     */
    public ProviderRegistrationException(String message) {
        super(message);
    }

    /**
     * Constructs the exception with only a cause.
     *
     * @param cause The cause
     * @since 1.0.0
     */
    public ProviderRegistrationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs the exception with a message and cause.
     *
     * @param message The message
     * @param cause The cause
     * @since 1.0.0
     */
    public ProviderRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
