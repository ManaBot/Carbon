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

import static com.google.common.base.Preconditions.checkNotNull;

import uk.jamierocks.mana.carbon.Carbon;

/**
 * Provides static access to the methods defined in {@link ExceptionService}.
 * In failure to retrieve the exception service, it uses a fallback.
 *
 * @author Jamie Mansfield
 * @since 1.1.0
 */
public final class ExceptionReporter {

    /**
     * The exception service to use when the service is unable to be fetched from the registry.
     */
    private static final ExceptionService FALLBACK = new FallbackExceptionService();

    /**
     * Handles reporting an {@link Exception}, with the default message.
     *
     * @param throwable The exception
     * @see ExceptionService#report(Throwable)
     * @since 1.1.0
     */
    public static void report(Throwable throwable) {
        report("Carbon has experienced an error!", throwable);
    }

    /**
     * Handles reporting an {@link Exception}, with a message.
     *
     * @param message The message
     * @param throwable The exception
     * @see ExceptionService#report(String, Throwable)
     * @since 1.1.0
     */
    public static void report(String message, Throwable throwable) {
        checkNotNull(message, "message is null!");
        Carbon.getCarbon().getServiceRegistry()
                .provideOrFallback(ExceptionService.class, FALLBACK).report(message, throwable);
    }
}
